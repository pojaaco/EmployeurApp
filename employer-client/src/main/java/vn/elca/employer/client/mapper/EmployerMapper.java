package vn.elca.employer.client.mapper;

import org.mapstruct.*;
import vn.elca.employer.client.model.view.EmployerView;
import vn.elca.employer.common.EmployerGetRequest;
import vn.elca.employer.common.EmployerProto;

@Mapper(
        componentModel = "spring",
        uses = {BasicMapper.class, EmployeeMapper.class},
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED
)
public abstract class EmployerMapper {
    @Mapping(source = "employees", target = "employeesList")
    public abstract EmployerProto toProto(EmployerView view);

    @Mapping(source = "employeesList", target = "employees")
    public abstract EmployerView toView(EmployerProto proto);

    public abstract EmployerGetRequest toRequest(EmployerView view);
}
