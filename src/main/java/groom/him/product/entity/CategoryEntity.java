package groom.him.product.entity;

import groom.him.core.entity.AuditingFields;
import groom.him.product.entity.constant.CategoryType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CATEGORY")
public class CategoryEntity extends AuditingFields {

    @Id
    @Column(name = "CATEGORY_ID")
    private String categoryId;

    @Enumerated(EnumType.STRING)
    @Column(name = "CATEGORY_TYPE")
    private CategoryType categoryType;

    @Column(name = "DEPTH")
    private Integer depth;

    @Column(name = "UPPER_CATEGORY_ID")
    private String upperCategoryId;

    @Column(name = "CATEGORY_NM")
    private String categoryName;

    @Column(name = "IS_LEAF")
    private Boolean isLeaf;
}