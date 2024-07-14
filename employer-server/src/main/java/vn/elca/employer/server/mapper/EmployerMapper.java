package vn.elca.employer.server.mapper;

import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import vn.elca.employer.common.*;
import vn.elca.employer.server.model.criteria.EmployerDeleteCriteria;
import vn.elca.employer.server.model.criteria.EmployerGetCriteria;
import vn.elca.employer.server.model.dto.EmployerDto;
import vn.elca.employer.server.model.entity.Employer;
import vn.elca.employer.server.repository.EmployerRepository;

@Mapper(
        componentModel = "spring",
        uses = {BasicMapper.class, EmployeeMapper.class},
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED
)
public abstract class EmployerMapper {
    @Autowired
    EmployerRepository employerRepository;

    @Mapping(source = "startDate", target = "startDate", dateFormat = ConstantContainer.DATE_FORMAT)
    @Mapping(source = "endDate", target = "endDate", dateFormat = ConstantContainer.DATE_FORMAT)
    public abstract Employer toEntity(EmployerDto dto);

    @Mapping(source = "startDate", target = "startDate", dateFormat = ConstantContainer.DATE_FORMAT)
    @Mapping(source = "endDate", target = "endDate", dateFormat = ConstantContainer.DATE_FORMAT)
    public abstract EmployerDto toDto(Employer entity);

    @Mapping(source = "employeesList", target = "employees")
    public abstract EmployerDto toDto(EmployerProto proto);

    @Mapping(source = "employees", target = "employeesList")
    public abstract EmployerProto toProto(EmployerDto dto);

    public abstract EmployerGetCriteria toCriteria(EmployerGetRequest request);

    public abstract EmployerDeleteCriteria toCriteria(EmployerDeleteRequest request);

    public Employer getEntityFromId(Long id) {
        if (id == null) {
            return null;
        }

        return employerRepository.findById(id).orElse(null);
    }

    public Long getIdFromEntity(Employer entity) {
        if (entity == null) {
            return null;
        }

        return entity.getId();
    }
}
