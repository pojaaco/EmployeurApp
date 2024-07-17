package vn.elca.employer.server.mapper;

import com.google.protobuf.*;
import org.mapstruct.Mapper;
import vn.elca.employer.common.CommonConstants;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Mapper(
        componentModel = "spring"
)
public abstract class BasicMapper {
    public String toString(StringValue value) {
        if (value == null) {
            return null;
        }

        return value.getValue();
    }

    StringValue toStringValue(String value) {
        if (value == null) {
            return null;
        }

        return StringValue.of(value);
    }

    public LocalDate toDate(StringValue value) {
        if (value == null) {
            return null;
        }

        return LocalDate.parse(value.getValue(), DateTimeFormatter.ofPattern(CommonConstants.DATE_FORMAT));
    }

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
}
