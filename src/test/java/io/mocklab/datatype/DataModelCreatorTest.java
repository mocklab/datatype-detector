package io.mocklab.datatype;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.junit.Test;

import java.io.InputStream;

import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class DataModelCreatorTest {

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void generates_simple_model_with_no_nesting() throws Exception {
        JsonNode exampleNode = mapper.readTree(loadFile("SimpleJsonExample.json"));

        DataModelCreator modelCreator = new DataModelCreator(mapper);

        JsonNode model = modelCreator.generateModel(exampleNode);

        assertThat(model.get("first_name").asText(), is(DataType.FIRST_NAME.name()));
        assertThat(model.get("address_line_1").asText(), is(DataType.ADDRESS_LINE_1.name()));
    }

    @Test
    public void generates_model_with_nesting() throws Exception {
        JsonNode exampleNode = mapper.readTree(loadFile("JsonExample.json"));

        DataModelCreator modelCreator = new DataModelCreator(mapper);

        JsonNode model = modelCreator.generateModel(exampleNode);

        assertThat(model.get("first_name").asText(), is(DataType.FIRST_NAME.name()));
        assertThat(model.get("date_of_birth").asText(), is(DataType.ISO_LOCAL_DATE.name()));
        assertThat(model.get("address").get("address_line_1").asText(), is(DataType.ADDRESS_LINE_1.name()));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void generates_model_with_nesting_and_arrays() throws Exception {
        JsonNode exampleNode = mapper.readTree(loadFile("JsonExampleArrays.json"));

        DataModelCreator modelCreator = new DataModelCreator(mapper);

        JsonNode model = modelCreator.generateModel(exampleNode);

        assertThat(model.get("first_name").asText(), is(DataType.FIRST_NAME.name()));
        assertThat(model.get("date_of_birth").asText(), is(DataType.ISO_LOCAL_DATE.name()));
        assertThat(model.get("address").get("address_line_1").asText(), is(DataType.ADDRESS_LINE_1.name()));
        assertThat(model.get("colours"), hasItems(
                withTextValue(DataType.COLOUR.name()),
                withTextValue(DataType.COLOUR.name()),
                withTextValue(DataType.COLOUR.name())
        ));
    }

    private static Matcher<JsonNode> withTextValue(final String expectedValue) {
        return new TypeSafeDiagnosingMatcher<JsonNode>() {
            @Override
            protected boolean matchesSafely(JsonNode item, Description mismatchDescription) {
                return item.asText().equals(expectedValue);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("a JsonNode with text value " + expectedValue);
            }
        };
    }

    private InputStream loadFile(String name) {
        try {
            return Thread.currentThread().getContextClassLoader().getResourceAsStream(name);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
