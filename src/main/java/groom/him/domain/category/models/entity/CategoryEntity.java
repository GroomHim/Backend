package groom.him.domain.category.models.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import groom.him.core.models.entity.AuditingFields;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "CATEGORY")
@Entity
public class CategoryEntity extends AuditingFields {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Integer categoryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_category_id")
    private CategoryEntity parentCategory;

    @OneToMany(mappedBy = "parentCategory")
    @JsonIgnore
    private List<CategoryEntity> children;

    @NotNull
    @Column(name = "depth")
    private Integer depth;

    @NotNull
    @Column(length = 15, name = "category_name")
    private String categoryName;

    @NotNull
    @Column(name = "is_leaf")
    private Boolean isLeaf;
}