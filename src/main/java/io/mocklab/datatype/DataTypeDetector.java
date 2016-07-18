package io.mocklab.datatype;

import java.util.Arrays;
import java.util.Optional;

public class DataTypeDetector {

    public DataType detect(String value) {
        return detect("", value);
    }

    public DataType detect(String attributeName, String value) {
        Optional<DataType> matchingDataType =
                Arrays.stream(DataType.values())
                .filter(dataType -> dataType.matches(attributeName, value))
                .findFirst();

        return matchingDataType.orElse(null);
    }
}
