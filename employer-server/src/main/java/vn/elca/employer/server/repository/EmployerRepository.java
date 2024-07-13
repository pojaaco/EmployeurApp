package vn.elca.employer.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import vn.elca.employer.server.model.entity.Employer;

@Repository
public interface EmployerRepository extends JpaRepository<Employer, Long>, QuerydslPredicateExecutor<Employer>, EmployerRepositoryCustom {
    @Query(value = "SELECT NEXT VALUE FOR NumberSeq", nativeQuery = true)
    Long getNextSeqValue();
}
