package org.choongang.thesis.entities;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QThesis is a Querydsl query type for Thesis
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QThesis extends EntityPathBase<Thesis> {

    private static final long serialVersionUID = 1818447603L;

    public static final QThesis thesis = new QThesis("thesis");

    public final org.choongang.global.entities.QBaseMemberEntity _super = new org.choongang.global.entities.QBaseMemberEntity(this);

    public final EnumPath<org.choongang.thesis.constants.Category> category = createEnum("category", org.choongang.thesis.constants.Category.class);

    public final StringPath contributor = createString("contributor");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final StringPath createdBy = _super.createdBy;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deletedAt = _super.deletedAt;

    public final StringPath email = createString("email");

    public final ListPath<Field, QField> fields = this.<Field, QField>createList("fields", Field.class, QField.class, PathInits.DIRECT2);

    public final StringPath gid = createString("gid");

    public final StringPath language = createString("language");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    //inherited
    public final StringPath modifiedBy = _super.modifiedBy;

    public final StringPath poster = createString("poster");

    public final StringPath publisher = createString("publisher");

    public final StringPath reference = createString("reference");

    public final StringPath thAbstract = createString("thAbstract");

    public final NumberPath<Long> tid = createNumber("tid", Long.class);

    public final StringPath title = createString("title");

    public final StringPath toc = createString("toc");

    public final StringPath userName = createString("userName");

    public final NumberPath<Integer> viewCount = createNumber("viewCount", Integer.class);

    public final BooleanPath visible = createBoolean("visible");

    public QThesis(String variable) {
        super(Thesis.class, forVariable(variable));
    }

    public QThesis(Path<? extends Thesis> path) {
        super(path.getType(), path.getMetadata());
    }

    public QThesis(PathMetadata metadata) {
        super(Thesis.class, metadata);
    }

}

