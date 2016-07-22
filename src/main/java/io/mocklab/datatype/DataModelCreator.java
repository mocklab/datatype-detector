package io.mocklab.datatype;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ContainerNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class DataModelCreator {

    private final ObjectMapper mapper;
    private final DataTypeDetector dataTypeDetector = new DataTypeDetector();

    public DataModelCreator(ObjectMapper mapper) {
        this.mapper = mapper;
    }


    public JsonNode generateModel2(JsonNode exampleRootNode) {

        DataTypeDetector dataTypeDetector = new DataTypeDetector();

        ObjectNode templateNode = mapper.createObjectNode();

        FieldsAdapter adapter = new FieldsAdapter(exampleRootNode);
        for (Map.Entry<String, JsonNode> entry: adapter) {
            String field = entry.getKey();
            JsonNode valueNode = entry.getValue();
            String value = valueNode.asText();

            if (valueNode instanceof ObjectNode) {
                JsonNode nestedTemplateNode = generateModel2(valueNode);
                templateNode.set(field, nestedTemplateNode);
            } else if (valueNode instanceof ArrayNode) {
                ArrayNode arrayNode = (ArrayNode) valueNode;
                Iterable<JsonNode> iterable = arrayNode::elements;
                List<JsonNode> dataTypes = StreamSupport.stream(iterable.spliterator(), false)
                        .map(this::generateModel2)
                        .collect(Collectors.toList());
                ArrayNode newArray = templateNode.putArray(field);
                newArray.addAll(dataTypes);
            } else {
                String dataType = dataTypeDetector.detect(field, value).toString();
                templateNode.put(field, dataType);
            }

        }
        System.out.println(templateNode);

        return templateNode;
    }

    public JsonNode generateModel(JsonNode exampleRootNode) {
        return generateModel("root", exampleRootNode);
    }

    public JsonNode generateModel(String key, JsonNode exampleRootNode) {
        if (exampleRootNode instanceof ObjectNode) {
            ObjectNode templateNode = mapper.createObjectNode();
            FieldsAdapter adapter = new FieldsAdapter(exampleRootNode);
            for (Map.Entry<String, JsonNode> entry: adapter) {
                String field = entry.getKey();
                JsonNode valueNode = entry.getValue();
                JsonNode nestedTemplateNode = generateModel(field, valueNode);
                templateNode.set(field, nestedTemplateNode);
            }

            return templateNode;
        } else if (exampleRootNode instanceof ArrayNode) {
            ArrayNode arrayNode = (ArrayNode) exampleRootNode;
            Iterable<JsonNode> iterable = arrayNode::elements;
            List<JsonNode> dataTypes = StreamSupport.stream(iterable.spliterator(), false)
                    .map(this::generateModel)
                    .collect(Collectors.toList());

            ArrayNode newArray = mapper.createArrayNode();
            newArray.addAll(dataTypes);

            return newArray;

        } else {
            return new TextNode(dataTypeDetector.detect(key, exampleRootNode.asText()).toString());
        }
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
