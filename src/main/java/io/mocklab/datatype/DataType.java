package io.mocklab.datatype;

public enum DataType {

    EMAIL_ADDRESS {
        @Override
        boolean matches(String value) {
            return value.matches("^.+@.+\\.[a-zA-Z]{2,}$");
        }
    },

    UNKNOWN {
        @Override
        boolean matches(String value) {
            return true;
        }
    };

    abstract boolean matches(String value);
}
