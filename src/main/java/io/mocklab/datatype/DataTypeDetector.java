package io.mocklab.datatype;

import java.util.Arrays;
import java.util.Optional;

public class DataTypeDetector {

    public DataType detect(String value) {
        Optional<DataType> matchingDataType =
                Arrays.stream(DataType.values())
                .filter(dataType -> dataType.matches(value))
                .findFirst();

        return matchingDataType.orElse(null);
    }
}
