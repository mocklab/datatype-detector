package io.mocklab.datatype;

import com.neovisionaries.i18n.CountryCode;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.UUID;

public enum DataType {

    EMAIL_ADDRESS {
        @Override
        boolean matches(String fieldName, String value) {
            return value.matches("^.+@.+\\.[a-zA-Z]{2,}$");
        }
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
    UNKNOWN {
        @Override
        boolean matches(String fieldName, String value) {
            return true;
        }
    };

    private static boolean dateTimeMatches(DateTimeFormatter formatter, String value) {
        try {
            formatter.parse(value);
            return true;
        }   catch (DateTimeParseException e) {
            return false;
        }
    }

    abstract boolean matches(String fieldName, String value);
}
