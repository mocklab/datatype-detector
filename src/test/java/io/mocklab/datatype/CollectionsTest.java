package io.mocklab.datatype;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.Test;

import java.io.StringWriter;
import java.util.*;
import java.util.stream.Stream;

import static java.util.Arrays.asList;


public class CollectionsTest {

    @Test
    public void maps() {
        // Create an unsorted map with String keys and String values
        // Add 3 items of your choosing

        // Retrieve and print one of the items

        // Print all of the keys


        Map<String, String> myMap = new HashMap<>();
            myMap.put("Fanta", "Orange flavour");
            myMap.put("Sprite", "Lemonade flavour");
            myMap.put("Dr Pepper", "Cherry flavour");

        System.out.println(myMap.get("Sprite"));
        System.out.println(myMap.keySet());


    }

    @Test
    public void moreMaps() {
        List<String> names = asList("jeff", "danny", "cuthbert", "hubert");

        Map<String, Integer> namesMap = new HashMap<>();

        for (String name : names) {
            Integer length = name.length();
            namesMap.put(name, length);
        }

        System.out.println(namesMap);


        // Create a map from the list of names where the key is the name and the value is the length of the name
    }

    @Test
    public void list() {
        List<Integer> numbers = new ArrayList<>();
        numbers.add(1);
        numbers.add(3);
        numbers.add(5);
        numbers.add(7);
        numbers.add(9);

        // make a new list of numbers populated with the above list's numbers multiplied by two

        List<Integer> doubledNumbers = new ArrayList<>();
        for (Integer value : numbers) {
            int doubledValue = value * 2;
            doubledNumbers.add(doubledValue);

        }
        System.out.println(doubledNumbers);

    }

    @Test
    public void json() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree("{ \"name\": \"Tom\", \"address_line_1\": \"4 Aylmer Road\" }");

        DataTypeDetector dataTypeDetector = new DataTypeDetector();

        ObjectNode templateNode = mapper.createObjectNode();

        FieldsAdapter adapter = new FieldsAdapter(rootNode);
        for (Map.Entry<String, JsonNode> entry: adapter) {
            String field = entry.getKey();
            String value = entry.getValue().toString();

            String dataType = dataTypeDetector.detect(field, value).toString();

            templateNode.put(field, dataType);

        }
        System.out.println(templateNode);
    }


    private static Iterable<Map.Entry<String, JsonNode>> fields(final JsonNode node) {
        return new Iterable<Map.Entry<String, JsonNode>>() {
            @Override
            public Iterator<Map.Entry<String, JsonNode>> iterator() {
                return node.fields();
            }
        };
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
