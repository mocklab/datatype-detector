package io.mocklab.datatype;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ContainerNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.Iterator;
import java.util.Map;

public class DataModelCreator {

    private final ObjectMapper mapper;

    public DataModelCreator(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public DataModelCreator() {
        this(new ObjectMapper());
    }

    public JsonNode generateModel(JsonNode exampleRootNode) {

        DataTypeDetector dataTypeDetector = new DataTypeDetector();

        ObjectNode templateNode = mapper.createObjectNode();

        FieldsAdapter adapter = new FieldsAdapter(exampleRootNode);
        for (Map.Entry<String, JsonNode> entry: adapter) {
//            containerChecker(exampleRootNode);
            String field = entry.getKey();
            JsonNode valueNode = entry.getValue();
            String value = valueNode.asText();

            if (valueNode instanceof ContainerNode) {
                JsonNode nestedTemplateNode = generateModel(valueNode);
                templateNode.set(field, nestedTemplateNode);
            } else {
                String dataType = dataTypeDetector.detect(field, value).toString();
                templateNode.put(field, dataType);
            }

        }
        System.out.println(templateNode);

        return templateNode;

    }

    private String containerChecker(JsonNode node) {

        for (JsonNode childNode : node) {
            if (childNode instanceof ObjectNode) {
                containerChecker(childNode);
            } else {
                String nodeContent = childNode.asText();
            }
        }

        return null;
    }


    public static class FieldsAdapter implements Iterable<Map.Entry<String, JsonNode>> {

        private JsonNode node;

        public FieldsAdapter(JsonNode node) {
            this.node = node;
        }

        @Override
        public Iterator<Map.Entry<String, JsonNode>> iterator() {
            return node.fields();
        }
    }
}
