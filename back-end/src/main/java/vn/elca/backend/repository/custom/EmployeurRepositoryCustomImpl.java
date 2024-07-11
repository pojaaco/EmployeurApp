package vn.elca.backend.repository.custom;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import vn.elca.backend.model.criteria.EmployeurSearchCriteria;
import vn.elca.backend.model.entity.Employeur;
import vn.elca.backend.model.entity.QEmployeur;
import vn.elca.common.Caisse;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class EmployeurRepositoryCustomImpl implements EmployeurRepositoryCustom {
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Employeur> searchByCriteria(EmployeurSearchCriteria criteria) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        QEmployeur qEmployeur = QEmployeur.employeur;
        BooleanExpression predicate = qEmployeur.isNotNull();

        if (criteria.getCaisse() != Caisse.CAISSE_UNKNOWN) {
            predicate = predicate.and(qEmployeur.caisse.eq(criteria.getCaisse()));
        }
        if (criteria.getNumber() != null) {
            predicate = predicate.and(qEmployeur.number.containsIgnoreCase(criteria.getNumber()));
        }
        if (criteria.getName() != null) {
            predicate = predicate.and(qEmployeur.name.containsIgnoreCase(criteria.getName()));
        }
        if (criteria.getNumberIDE() != null) {
            predicate = predicate.and(qEmployeur.numberIDE.containsIgnoreCase(criteria.getNumberIDE()));
        }
        if (criteria.getStartingDate() != null) {
            predicate = predicate.and(qEmployeur.startingDate.goe(criteria.getStartingDate()));
        }
        if (criteria.getEndDate() != null) {
            predicate = predicate.and(qEmployeur.endDate.loe(criteria.getEndDate()));
        }

        return queryFactory.selectFrom(qEmployeur)
                .where(predicate)
                .fetch();
    }
}
