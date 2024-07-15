package vn.elca.employer.client.mapper;

import com.google.protobuf.Int64Value;
import com.google.protobuf.StringValue;
import org.mapstruct.Mapper;

@Mapper(
        componentModel = "spring"
)
public abstract class BasicMapper {
    public Long toLong(Int64Value value) {
        if (value == null) {
            return null;
        }

        return value.getValue();
    }

    public Int64Value toInt64(Long value) {
        if (value == null) {
            return null;
        }

        return Int64Value.of(value);
    }

    public String toString(StringValue value) {
        if (value == null) {
            return null;
        }

        return value.getValue();
    }

    public StringValue toStringValue(String value) {
        if (value == null) {
            return null;
        }

        return StringValue.of(value);
    }
}
