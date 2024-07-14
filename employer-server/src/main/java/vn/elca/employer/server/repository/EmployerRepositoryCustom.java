package vn.elca.employer.server.repository;

import vn.elca.employer.server.model.criteria.EmployerGetCriteria;
import vn.elca.employer.server.model.entity.Employer;

import java.util.List;

public interface EmployerRepositoryCustom {
    List<Employer> getByCriteria(EmployerGetCriteria criteria);

    Employer saveEmployer(Employer employer);
}
