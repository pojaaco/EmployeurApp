package vn.elca.backend.util.mapper;

import com.google.protobuf.*;
import org.mapstruct.Mapper;
import vn.elca.common.ConstantContainer;

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

    public LocalDate toDate(StringValue value) {
        if (value == null) {
            return null;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(ConstantContainer.DATE_FORMAT);
        return LocalDate.parse(value.getValue(), formatter);
    }

    public Long toLong(Int64Value value) {
        if (value == null) {
            return null;
        }

        return value.getValue();
    }

    public Integer toInteger(Int32Value value) {
        if (value == null) {
            return null;
        }

        return value.getValue();
    }
}
