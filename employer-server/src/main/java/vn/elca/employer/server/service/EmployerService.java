package vn.elca.employer.server.service;

import vn.elca.employer.server.model.criteria.EmployerDelCriteria;
import vn.elca.employer.server.model.criteria.EmployerGetCriteria;
import vn.elca.employer.server.model.dto.EmployerDto;

import java.util.List;

public interface EmployerService {
    List<EmployerDto> getByCriteria(EmployerGetCriteria criteria);

    void saveEmployer(EmployerDto employerDto);

    void delByCriteria(EmployerDelCriteria criteria);
}
