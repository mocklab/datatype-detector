package io.mocklab.datatype;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class DataTypeTest {

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
        assertThat(dataTypeDetector.detect("name", "Whatever"), is(DataType.FIRST_NAME));
    }

    @Test
    public void matches_a_surname() {
        assertThat(dataTypeDetector.detect("LastName", "Whatever"), is(DataType.LAST_NAME));
        assertThat(dataTypeDetector.detect("Surname", "Whatever"), is(DataType.LAST_NAME));
        assertThat(dataTypeDetector.detect("Family_Name", "Whatever"), is(DataType.LAST_NAME));
        assertThat(dataTypeDetector.detect("Family Name", "Whatever"), is(DataType.LAST_NAME));

    }
    @Test
    public void matches_a_zip_code() {
        assertThat(dataTypeDetector.detect("zipcode", "12345"), is(DataType.ZIP_CODE));
        assertThat(dataTypeDetector.detect("ZIP", "00000"), is(DataType.ZIP_CODE));
        assertThat(dataTypeDetector.detect("Post Code", "99999"), is(DataType.ZIP_CODE));
        assertThat(dataTypeDetector.detect("Postal_code", "12345"), is(DataType.ZIP_CODE));

    }

    @Test
    public void matches_a_postcode() {
        assertThat(dataTypeDetector.detect("YO30 6AS"), is(DataType.POSTCODE));
        assertThat(dataTypeDetector.detect("YO306AS"), is(DataType.POSTCODE));


    }

    @Test
    public void matches_a_city() {
        assertThat(dataTypeDetector.detect("Town","Cádiz"),is(DataType.CITY));
        assertThat(dataTypeDetector.detect("Town","New York City"),is(DataType.CITY));
        assertThat(dataTypeDetector.detect("Postal City","York"),is(DataType.CITY));
        assertThat(dataTypeDetector.detect("Town/City","Camden Town"),is(DataType.CITY));
    }

    @Test
    public void matches_a_county() {
        assertThat(dataTypeDetector.detect("County", "Yorkshire"), is(DataType.COUNTY));
        assertThat(dataTypeDetector.detect("county", "Lancashire"), is(DataType.COUNTY));
        assertThat(dataTypeDetector.detect("Postal County", "Some county down south"), is(DataType.COUNTY));
    }

    @Test
    public void matches_a_state() {
        assertThat(dataTypeDetector.detect("State", "Georgia"), is(DataType.STATE));
    }
    @Test
    public void matches_a_district() {
        assertThat(dataTypeDetector.detect("District", "Whatever"), is(DataType.DISTRICT));
    }
    @Test
    public void matches_an_address_line_1() {
        assertThat(dataTypeDetector.detect("Address line 1","2 The Avenue"), is(DataType.ADDRESS_LINE_1));
        assertThat(dataTypeDetector.detect("Address line 1","Apartment 4, Mill House, Bond Street"), is(DataType.ADDRESS_LINE_1));
        assertThat(dataTypeDetector.detect("Address line one","Farmhouse, Farm Lane"), is(DataType.ADDRESS_LINE_1));
    }
    @Test
    public void matches_an_addressreturn_not_line_1() {
        assertThat(dataTypeDetector.detect("Address line 2","whatever"), is(DataType.ADDRESS_LINE_NOT_1));
        assertThat(dataTypeDetector.detect("Address line 2","whatever, somewhere"), is(DataType.ADDRESS_LINE_NOT_1));
        assertThat(dataTypeDetector.detect("Address line two","32 whatevers"), is(DataType.ADDRESS_LINE_NOT_1));
    }

    @Test
    public void matches_a_company_name() {
        assertThat(dataTypeDetector.detect("Company name","Bresmed"), is(DataType.COMPANY_NAME));
        assertThat(dataTypeDetector.detect("Firm","energized work"), is(DataType.COMPANY_NAME));
    }

    @Test
    public void matches_an_international_phone_number() {
        assertThat(dataTypeDetector.detect("+447590385897"), is(DataType.INTERNATIONAL_PHONE_NUMBER));
        assertThat(dataTypeDetector.detect("+447590-385897"), is(DataType.INTERNATIONAL_PHONE_NUMBER));
        assertThat(dataTypeDetector.detect("+44)(*&^%$£7590-385897"), is(DataType.INTERNATIONAL_PHONE_NUMBER));

    }
    @Test
    public void matches_a_local_phone_number() {
        assertThat(dataTypeDetector.detect("07590385897"), is(DataType.LOCAL_PHONE_NUMBER));
        assertThat(dataTypeDetector.detect("07590-385897"), is(DataType.LOCAL_PHONE_NUMBER));
        assertThat(dataTypeDetector.detect("222-222-2222"), is(DataType.LOCAL_PHONE_NUMBER));
    }
}



