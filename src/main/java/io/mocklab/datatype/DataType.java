package io.mocklab.datatype;

import com.neovisionaries.i18n.CountryCode;
import com.neovisionaries.i18n.CurrencyCode;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.regex.Pattern;

import static java.util.Arrays.asList;

public enum DataType {

    EMAIL_ADDRESS {
        @Override
        boolean matches(String fieldName, String value) {
            return value.matches("^.+@.+\\.[a-zA-Z]{2,}$");
        }
    },
    ADDRESS_LINE_1 {
        @Override
        boolean matches(String fieldName, String value) {
            String canonicalFieldName = canonicalise(fieldName);
            return canonicalFieldName.contains("addressline1") || canonicalFieldName.contains("addresslineone")
                    && value.matches("\\w+\\s*+.*");
        }
    },
    ADDRESS_LINE_NOT_1 {
        @Override
        boolean matches(String fieldName, String value) {
            String canonicalFieldName = canonicalise(fieldName);
            return canonicalFieldName.contains("addressline")
                && (!canonicalFieldName.contains("addressline1") || !canonicalFieldName.contains("addresslineone"))
                    && value.matches("\\w+\\s*+.*");
        }
    },
    ZIP_CODE {
        @Override
        boolean matches(String fieldName, String value) {
            String canonicalFieldName = canonicalise(fieldName);
            return asList("postcode", "postalcode", "zipcode", "zip").contains(canonicalFieldName)
                    && value.matches("[0-9]{5}");
        }
    },
    POSTCODE {
        @Override
        boolean matches(String fieldName, String value) {
            return value.matches("\\p{Alpha}+\\p{Alnum}{1,3}+\\s*\\p{Digit}+\\p{Alpha}{2}");
        }
    },
    CITY {
        @Override
        boolean matches(String fieldName, String value) {
            String canonicalFieldName = canonicalise(fieldName);
            return canonicalFieldName.contains("city") || canonicalFieldName.contains("town")
                    && value.matches("\\w+\\s*+.*");
        }
    },
    COUNTY {
        @Override
        boolean matches(String fieldName, String value) {
            String canonicalFieldName = canonicalise(fieldName);
            return canonicalFieldName.contains("county")
                    && value.matches("\\w+\\s*+.*");

        }
    },
    STATE {
        @Override
        boolean matches(String fieldName, String value) {
            String canonicalFieldName = canonicalise(fieldName);
            return canonicalFieldName.contains("state")
                    && value.matches("\\w+\\s*+.*");
        }
    },
    DISTRICT {
        @Override
        boolean matches(String fieldName, String value) {
            String canonicalFieldName = canonicalise(fieldName);
            return canonicalFieldName.contains("district")
                    && value.matches("\\w+\\s*+.*");        }
    },
    UUID {
        @Override
        boolean matches(String fieldName, String value) {
            try {
                java.util.UUID.fromString(value);
                return true;
            } catch (IllegalArgumentException e) {
                return false;
            }

        }
    },
    COUNTRY_CODE_2 {
        @Override
        boolean matches(String fieldName, String value) {
            return Arrays.stream(CountryCode.values())
                    .anyMatch(countryCode -> countryCode.getAlpha2().equalsIgnoreCase(value));
        }
    },
    COUNTRY_CODE_3 {
        @Override
        boolean matches(String fieldName, String value) {
            return Arrays.stream(CountryCode.values())
                    .anyMatch(countryCode ->
                            countryCode.getAlpha3() != null &&
                            countryCode.getAlpha3().equalsIgnoreCase(value));
        }
    },
    CURRENCY_CODE {
        @Override
        boolean matches(String fieldName, String value) {
            return Arrays.stream(CurrencyCode.values())
                    .anyMatch(currencyCode ->
                            currencyCode.getCurrency() != null &&
                            currencyCode.getCurrency().getCurrencyCode() != null &&
                            currencyCode.getCurrency().getCurrencyCode().equalsIgnoreCase(value));
        }
    },
    COMPANY_NAME {
        @Override
        boolean matches(String fieldName, String value) {
            String canonicalFieldName = canonicalise(fieldName);
            return asList("companyname","compname","company","business","businessname","firm","firmname").contains(canonicalFieldName);
        }
    },

    ISO_INSTANT {
        @Override
        boolean matches(String fieldName, String value) {
            return dateTimeMatches(DateTimeFormatter.ISO_INSTANT, value);
        }
    },
    ISO_OFFSET_DATE_TIME {
        @Override
        boolean matches(String fieldName, String value) {
            return dateTimeMatches(DateTimeFormatter.ISO_OFFSET_DATE_TIME, value);
        }
    },
    ISO_LOCAL_DATE_TIME {
        @Override
        boolean matches(String fieldName, String value) {
            return dateTimeMatches(DateTimeFormatter.ISO_LOCAL_DATE_TIME, value);
        }
    },
    ISO_DATE_TIME {
        @Override
        boolean matches(String fieldName, String value) {
            return dateTimeMatches(DateTimeFormatter.ISO_DATE_TIME, value);
        }
    },


    ISO_LOCAL_DATE {
        @Override
        boolean matches(String fieldName, String value) {
            return dateTimeMatches(DateTimeFormatter.ISO_LOCAL_DATE, value);
        }
    },
    ISO_DATE {
        @Override
        boolean matches(String fieldName, String value) {
            return dateTimeMatches(DateTimeFormatter.ISO_DATE, value);
        }
    },
    ISO_LOCAL_TIME {
        @Override
        boolean matches(String fieldName, String value) {
            return dateTimeMatches(DateTimeFormatter.ISO_LOCAL_TIME, value);
        }
    },
    ISO_TIME {
        @Override
        boolean matches(String fieldName, String value) {
            return dateTimeMatches(DateTimeFormatter.ISO_TIME, value);
        }
    },


    ISO_ZONED_DATE_TIME {
        @Override
        boolean matches(String fieldName, String value) {
            return dateTimeMatches(DateTimeFormatter.ISO_ZONED_DATE_TIME, value);
        }
    },
    ISO_ORDINAL_DATE {
        @Override
        boolean matches(String fieldName, String value) {
            return dateTimeMatches(DateTimeFormatter.ISO_ORDINAL_DATE, value);
        }
    },
    ISO_WEEK_DATE {
        @Override
        boolean matches(String fieldName, String value) {
            return dateTimeMatches(DateTimeFormatter.ISO_WEEK_DATE, value);
        }
    },

    BASIC_ISO_DATE {
        @Override
        boolean matches(String fieldName, String value) {
            return dateTimeMatches(DateTimeFormatter.BASIC_ISO_DATE, value);
        }
    },
    INTERNATIONAL_PHONE_NUMBER {
        @Override
        boolean matches(String fieldName, String value) {
            String canonicalValue = canonicalisePhoneNumber(value);
            return canonicalValue.matches("\\++\\p{Digit}{11,12}");
        }
    },
    LOCAL_PHONE_NUMBER {
        @Override
        boolean matches(String fieldName, String value) {
            String canonicalValue = DataType.canonicalisePhoneNumber(value);
            return canonicalValue.matches("\\p{Digit}{10,11}");        }
    },
    FIRST_NAME {
        @Override
        boolean matches(String fieldName, String value) {
            String canonicalFieldName = canonicalise(fieldName);
            return asList("name", "firstname", "fname","forename", "christianname", "givenname").contains(canonicalFieldName);
        }
    },
    LAST_NAME {
        @Override
        boolean matches(String fieldName, String value) {
            String canonicalFieldName = canonicalise(fieldName);
            return asList("surname", "familyname", "lastname", "sname", "lname").contains(canonicalFieldName);        }
    },
    SENTENCE {
        @Override
        boolean matches(String fieldName, String value) {
            return value.matches(".*\\w+\\s+.*") && !value.contains("\n");
        }
    },
    PARAGRAPH {
        @Override
        boolean matches(String fieldName, String value) {
            return PARAGRAPH_PATTERN.matcher(value).matches() && value.contains("\n");
        }
    },
    UNKNOWN {
        @Override
        boolean matches(String fieldName, String value) {
            return true;
        }
    };

    abstract boolean matches(String fieldName, String value);

    private static boolean dateTimeMatches(DateTimeFormatter formatter, String value) {
        try {
            formatter.parse(value);
            return true;
        }   catch (DateTimeParseException e) {
            return false;
        }
    }

    private static String canonicalise(String fieldName) {
        String canonicalFieldName = fieldName
                .replaceAll("\\p{Punct}+", "")
                .replaceAll("\\s+", "");
        return canonicalFieldName.toLowerCase();

    }

    private static String canonicalisePhoneNumber(String value) {
        String canonicalValue = value
               // .replaceAll("[\\p{Punct}[^\u002b]+]", "")
               // .replaceAll("\\s+", "");
                .replaceAll("[^\\p{Alnum}+]","");
        return canonicalValue;

    }



    private static final Pattern PARAGRAPH_PATTERN = Pattern.compile(
            ".*\\w+\\s+.*",
            Pattern.MULTILINE + Pattern.DOTALL
    );
}
