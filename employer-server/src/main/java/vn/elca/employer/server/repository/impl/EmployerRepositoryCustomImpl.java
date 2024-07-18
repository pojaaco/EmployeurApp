package vn.elca.employer.server.repository.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import vn.elca.employer.server.model.criteria.EmployerGetCriteria;
import vn.elca.employer.server.model.entity.Employer;
import vn.elca.employer.server.model.entity.QEmployer;
import vn.elca.employer.server.repository.EmployerRepositoryCustom;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class EmployerRepositoryCustomImpl implements EmployerRepositoryCustom {
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Employer> getByCriteria(EmployerGetCriteria criteria) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        QEmployer qEmployer = QEmployer.employer;
        BooleanExpression predicate = qEmployer.isNotNull();

        if (criteria.getId() != null) {
            predicate = predicate.and(qEmployer.id.eq(criteria.getId()));
        } else {
            predicate = predicate.and(qEmployer.fund.eq(criteria.getFund()));
            if (criteria.getNumber() != null) {
                predicate = predicate.and(qEmployer.number.containsIgnoreCase(criteria.getNumber()));
            }
            if (criteria.getName() != null) {
                predicate = predicate.and(qEmployer.name.containsIgnoreCase(criteria.getName()));
            }
            if (criteria.getNumberIde() != null) {
                predicate = predicate.and(qEmployer.numberIde.containsIgnoreCase(criteria.getNumberIde()));
            }
            if (criteria.getStartDate() != null) {
                predicate = predicate.and(qEmployer.startDate.goe(criteria.getStartDate()));
            }
            if (criteria.getEndDate() != null) {
                predicate = predicate.and(qEmployer.endDate.loe(criteria.getEndDate()));
            }
        }

        return queryFactory.selectFrom(qEmployer)
                .where(predicate)
                .fetch();
    }
}
