package vn.elca.employer.client.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import vn.elca.employer.client.model.view.EmployeeView;
import vn.elca.employer.common.EmployeeProto;

@Mapper(
        componentModel = "spring",
        uses = {BasicMapper.class, EmployerMapper.class}
)
public abstract class EmployeeMapper {
    public abstract EmployeeProto toProto(EmployeeView view);

    public abstract EmployeeView toView(EmployeeProto proto);
}
