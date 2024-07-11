package vn.elca.backend.util.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import vn.elca.backend.model.dto.EmployeeDto;
import vn.elca.backend.model.entity.Employee;
import vn.elca.common.ConstantContainer;
import vn.elca.common.EmployeeProto;

@Mapper(
        componentModel = "spring",
        uses = {EmployeurMapper.class}
)
public abstract class EmployeeMapper {
    @Mapping(source = "startingDate", target = "startingDate", dateFormat = ConstantContainer.DATE_FORMAT)
    @Mapping(source = "endDate", target = "endDate", dateFormat = ConstantContainer.DATE_FORMAT)
    @Mapping(source = "employeurId", target = "employeur")
    public abstract Employee toEntity(EmployeeDto dto);

    @Mapping(source = "startingDate", target = "startingDate", dateFormat = ConstantContainer.DATE_FORMAT)
    @Mapping(source = "endDate", target = "endDate", dateFormat = ConstantContainer.DATE_FORMAT)
    @Mapping(source = "employeur", target = "employeurId")
    public abstract EmployeeDto toDto(Employee entity);

    public abstract EmployeeDto toDto(EmployeeProto proto);

    public abstract EmployeeProto toProto(EmployeeDto dto);
}
