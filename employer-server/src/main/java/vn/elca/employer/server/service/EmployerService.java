package vn.elca.employer.server.service;

import vn.elca.employer.server.model.criteria.EmployerDeleteCriteria;
import vn.elca.employer.server.model.criteria.EmployerGetCriteria;
import vn.elca.employer.server.model.dto.EmployerDto;

import java.util.List;

public interface EmployerService {
    List<EmployerDto> getByCriteria(EmployerGetCriteria criteria);

    EmployerDto saveEmployer(EmployerDto employerDto);

    void deleteByCriteria(EmployerDeleteCriteria criteria);
}
