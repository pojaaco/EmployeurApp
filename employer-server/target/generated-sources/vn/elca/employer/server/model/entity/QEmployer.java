package vn.elca.employer.server.model.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QEmployer is a Querydsl query type for Employer
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QEmployer extends EntityPathBase<Employer> {

    private static final long serialVersionUID = -936365386L;

    public static final QEmployer employer = new QEmployer("employer");

    public final EnumPath<vn.elca.employer.common.Caisse> caisse = createEnum("caisse", vn.elca.employer.common.Caisse.class);

    public final SetPath<Employee, QEmployee> employees = this.<Employee, QEmployee>createSet("employees", Employee.class, QEmployee.class, PathInits.DIRECT2);

    public final DatePath<java.time.LocalDate> endDate = createDate("endDate", java.time.LocalDate.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final StringPath number = createString("number");

    public final StringPath numberIDE = createString("numberIDE");

    public final DatePath<java.time.LocalDate> startingDate = createDate("startingDate", java.time.LocalDate.class);

    public QEmployer(String variable) {
        super(Employer.class, forVariable(variable));
    }

    public QEmployer(Path<? extends Employer> path) {
        super(path.getType(), path.getMetadata());
    }

    public QEmployer(PathMetadata metadata) {
        super(Employer.class, metadata);
    }

}

