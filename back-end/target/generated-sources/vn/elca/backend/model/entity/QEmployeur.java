package vn.elca.backend.model.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QEmployeur is a Querydsl query type for Employeur
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QEmployeur extends EntityPathBase<Employeur> {

    private static final long serialVersionUID = 1198023003L;

    public static final QEmployeur employeur = new QEmployeur("employeur");

    public final EnumPath<vn.elca.common.Caisse> caisse = createEnum("caisse", vn.elca.common.Caisse.class);

    public final SetPath<Employee, QEmployee> employees = this.<Employee, QEmployee>createSet("employees", Employee.class, QEmployee.class, PathInits.DIRECT2);

    public final DatePath<java.time.LocalDate> endDate = createDate("endDate", java.time.LocalDate.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final StringPath number = createString("number");

    public final StringPath numberIDE = createString("numberIDE");

    public final DatePath<java.time.LocalDate> startingDate = createDate("startingDate", java.time.LocalDate.class);

    public QEmployeur(String variable) {
        super(Employeur.class, forVariable(variable));
    }

    public QEmployeur(Path<? extends Employeur> path) {
        super(path.getType(), path.getMetadata());
    }

    public QEmployeur(PathMetadata metadata) {
        super(Employeur.class, metadata);
    }

}

