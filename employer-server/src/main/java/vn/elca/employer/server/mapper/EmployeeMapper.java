package vn.elca.employer.server.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import vn.elca.employer.common.CommonConstants;
import vn.elca.employer.server.model.dto.EmployeeDto;
import vn.elca.employer.server.model.entity.Employee;
import vn.elca.employer.common.EmployeeProto;

@Mapper(
        componentModel = "spring",
        uses = {BasicMapper.class, EmployerMapper.class},
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
)
public abstract class EmployeeMapper {
    @Mapping(source = "startDate", target = "startDate", dateFormat = CommonConstants.DATE_FORMAT)
    @Mapping(source = "endDate", target = "endDate", dateFormat = CommonConstants.DATE_FORMAT)
    @Mapping(source = "employerId", target = "employer")
    public abstract Employee toEntity(EmployeeDto dto);

    @Mapping(source = "startDate", target = "startDate", dateFormat = CommonConstants.DATE_FORMAT)
    @Mapping(source = "endDate", target = "endDate", dateFormat = CommonConstants.DATE_FORMAT)
    @Mapping(source = "employer", target = "employerId")
    public abstract EmployeeDto toDto(Employee entity);

    public abstract EmployeeDto toDto(EmployeeProto proto);

    public abstract EmployeeProto toProto(EmployeeDto dto);
}
