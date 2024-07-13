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

        predicate = predicate.and(qEmployer.caisse.eq(criteria.getCaisse()));
        if (criteria.getNumber() != null) {
            predicate = predicate.and(qEmployer.number.containsIgnoreCase(criteria.getNumber()));
        }
        if (criteria.getName() != null) {
            predicate = predicate.and(qEmployer.name.containsIgnoreCase(criteria.getName()));
        }
        if (criteria.getNumberIDE() != null) {
            predicate = predicate.and(qEmployer.numberIDE.containsIgnoreCase(criteria.getNumberIDE()));
        }
        if (criteria.getStartingDate() != null) {
            predicate = predicate.and(qEmployer.startingDate.goe(criteria.getStartingDate()));
        }
        if (criteria.getEndDate() != null) {
            predicate = predicate.and(qEmployer.endDate.loe(criteria.getEndDate()));
        }

        return queryFactory.selectFrom(qEmployer)
                .where(predicate)
                .fetch();
    }

    @Override
    public void saveEmployer(Employer employer) {
        em.merge(employer);
    }
}
