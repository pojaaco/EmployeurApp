package vn.elca.backend.repository.custom;

import vn.elca.backend.model.criteria.EmployeurSearchCriteria;
import vn.elca.backend.model.entity.Employeur;

import java.util.List;

public interface EmployeurRepositoryCustom {
    List<Employeur> searchByCriteria(EmployeurSearchCriteria criteria);
}
