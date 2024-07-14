package vn.elca.employer.client.mapper;

import com.google.protobuf.Int64Value;
import com.google.protobuf.StringValue;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import vn.elca.employer.client.converter.EnumStringConverter;
import vn.elca.employer.common.Fund;

@Mapper(
        componentModel = "spring"
)
public abstract class BasicMapper {
    @Autowired
    private EnumStringConverter<Fund> fundConverter;

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

    public Fund toFund(String value) {
        if (value == null) {
            return null;
        }

        return fundConverter.fromString(value);
    }

    public String toString(Fund value) {
        if (value == null) {
            return null;
        }

        return fundConverter.toString(value);
    }
}
