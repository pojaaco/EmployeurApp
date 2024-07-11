package vn.elca.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import vn.elca.backend.model.entity.Employeur;
import vn.elca.backend.repository.custom.EmployeurRepositoryCustom;

@Repository
public interface EmployeurRepository extends JpaRepository<Employeur, Long>, QuerydslPredicateExecutor<Employeur>, EmployeurRepositoryCustom {
}
