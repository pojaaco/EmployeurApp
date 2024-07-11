package vn.elca.backend.util.mapper;

import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import vn.elca.backend.model.criteria.EmployeurSearchCriteria;
import vn.elca.backend.model.dto.EmployeurDto;
import vn.elca.backend.model.entity.Employeur;
import vn.elca.backend.repository.EmployeurRepository;
import vn.elca.common.ConstantContainer;
import vn.elca.common.EmployeurProto;
import vn.elca.common.EmployeurSearchRequest;

@Mapper(
        componentModel = "spring",
        uses = {BasicMapper.class, EmployeeMapper.class},
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED
)
public abstract class EmployeurMapper {
    @Autowired
    EmployeurRepository employeurRepository;

    @Mapping(source = "startingDate", target = "startingDate", dateFormat = ConstantContainer.DATE_FORMAT)
    @Mapping(source = "endDate", target = "endDate", dateFormat = ConstantContainer.DATE_FORMAT)
    public abstract Employeur toEntity(EmployeurDto dto);

    @Mapping(source = "startingDate", target = "startingDate", dateFormat = ConstantContainer.DATE_FORMAT)
    @Mapping(source = "endDate", target = "endDate", dateFormat = ConstantContainer.DATE_FORMAT)
    public abstract EmployeurDto toDto(Employeur entity);

    @Mapping(source = "employeesList", target = "employees")
    public abstract EmployeurDto toDto(EmployeurProto proto);

    @Mapping(source = "employees", target = "employeesList")
    public abstract EmployeurProto toProto(EmployeurDto dto);

    public abstract EmployeurSearchCriteria toCriteria(EmployeurSearchRequest request);

    public Employeur getEntityFromId(Long id) {
        return employeurRepository.findById(id).orElse(null);
    }

    public Long getIdFromEntity(Employeur entity) {
        return entity.getId();
    }
}
