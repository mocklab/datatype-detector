package io.mocklab.datatype;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class EmailAddressTest {

    private DataTypeDetector dataTypeDetector;

    @Before
    public void init() {
        dataTypeDetector = new DataTypeDetector();
    }

    @Test
    public void matches_a_valid_email_address() {
        DataType type = dataTypeDetector.detect("tom.akehurst@my.example.domain.com");

        assertThat(type, is(DataType.EMAIL_ADDRESS));
    }

    @Test
    public void does_not_match_an_invalid_email_address() {
        assertThat(dataTypeDetector.detect(""), is(DataType.UNKNOWN));
        assertThat(dataTypeDetector.detect("blahnotemail"), is(DataType.UNKNOWN));
        assertThat(dataTypeDetector.detect("1912873absdbfsdf17239-2903842"), is(DataType.UNKNOWN));
        assertThat(dataTypeDetector.detect("tom@wrong"), is(DataType.UNKNOWN));
    }
}
