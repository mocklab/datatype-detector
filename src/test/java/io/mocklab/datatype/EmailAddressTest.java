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

    @Test
    public void matches_a_uuid() {
        DataType type = dataTypeDetector.detect("15bbd75d-2a2e-4a1c-ac34-f30015e74405");

        assertThat(type, is(DataType.UUID));
    }

    @Test
    public void doesnt_match_an_invalid_UUID() {
        assertThat(dataTypeDetector.detect(""), is(DataType.UNKNOWN));
        assertThat(dataTypeDetector.detect("-2a2e-4a1c-ac34-f30015e74405"), is(DataType.UNKNOWN));
        assertThat(dataTypeDetector.detect("something"), is(DataType.UNKNOWN));
        assertThat(dataTypeDetector.detect("15bbd75d-2a2e--4a1c-ac34-f30015e74405"), is(DataType.UNKNOWN));

    }

    @Test
    public void matches_alpha2_country_code() {
        assertThat(dataTypeDetector.detect("GB"), is(DataType.COUNTRY_CODE_2));
        assertThat(dataTypeDetector.detect("gb"), is(DataType.COUNTRY_CODE_2));
    }

    @Test
    public void matches_alpha3_country_code() {
        assertThat(dataTypeDetector.detect("GBR"), is(DataType.COUNTRY_CODE_3));
        assertThat(dataTypeDetector.detect("gbr"), is(DataType.COUNTRY_CODE_3));

    }

    @Test
    public void matches_a_date_format() {
        assertThat(dataTypeDetector.detect("2016-07-18T13:51:18.646Z"), is(DataType.ISO_INSTANT));
        assertThat(dataTypeDetector.detect("2016-07-18T14:49:41.988+01:00"), is(DataType.ISO_OFFSET_DATE_TIME));
        assertThat(dataTypeDetector.detect("2016-07-18T15:10:11.582"), is(DataType.ISO_LOCAL_DATE_TIME));
        assertThat(dataTypeDetector.detect("2016-07-18T14:31:23.94+01:00[Europe/London]"), is(DataType.ISO_DATE_TIME));
        assertThat(dataTypeDetector.detect("2016-07-18"), is(DataType.ISO_LOCAL_DATE));
        assertThat(dataTypeDetector.detect("2016-07-18+01:00"), is(DataType.ISO_DATE));
        assertThat(dataTypeDetector.detect("14:47:19.429"), is(DataType.ISO_LOCAL_TIME));
        assertThat(dataTypeDetector.detect("14:48:38.181+01:00"), is(DataType.ISO_TIME));
        assertThat(dataTypeDetector.detect("2016-200+01:00"), is(DataType.ISO_ORDINAL_DATE));
        assertThat(dataTypeDetector.detect("2016-W29-1+01:00"), is(DataType.ISO_WEEK_DATE));
        assertThat(dataTypeDetector.detect("20160718+0100"), is(DataType.BASIC_ISO_DATE));


    }

    @Test
    public void matches_a_sentence() {
        assertThat(dataTypeDetector.detect("This is a sentence."), is(DataType.SENTENCE));


    }

    @Test
    public void matches_a_paragraph() {
        assertThat(dataTypeDetector.detect("This is not just a sentence.\nThis is a paragraph\n"), is(DataType.PARAGRAPH));

    }

    @Test
    public void matches_a_currency() {
        assertThat(dataTypeDetector.detect("GBP"), is(DataType.CURRENCY_CODE));

    }

    @Test
    public void matches_a_first_name() {
        assertThat(dataTypeDetector.detect("f_name", "Whatever"), is(DataType.FIRST_NAME));
        assertThat(dataTypeDetector.detect("First_Name", "Whatever"), is(DataType.FIRST_NAME));
        assertThat(dataTypeDetector.detect("ChristianName", "Whatever"), is(DataType.FIRST_NAME));
        assertThat(dataTypeDetector.detect("Givenname", "Whatever"), is(DataType.FIRST_NAME));
        assertThat(dataTypeDetector.detect("forename", "Whatever"), is(DataType.FIRST_NAME));
    }

    @Test
    public void matches_a_surname() {
        assertThat(dataTypeDetector.detect("LastName", "Whatever"), is(DataType.LAST_NAME));
        assertThat(dataTypeDetector.detect("Surname", "Whatever"), is(DataType.LAST_NAME));
        assertThat(dataTypeDetector.detect("Family_Name", "Whatever"), is(DataType.LAST_NAME));
        assertThat(dataTypeDetector.detect("Family Name", "Whatever"), is(DataType.LAST_NAME));

    }
}


