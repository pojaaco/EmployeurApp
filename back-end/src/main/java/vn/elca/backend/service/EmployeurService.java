package vn.elca.backend.service;

import vn.elca.backend.model.criteria.EmployeurSearchCriteria;
import vn.elca.backend.model.dto.EmployeurDto;

import java.util.List;

public interface EmployeurService {
    List<EmployeurDto> searchByCriteria(EmployeurSearchCriteria criteria);
}
