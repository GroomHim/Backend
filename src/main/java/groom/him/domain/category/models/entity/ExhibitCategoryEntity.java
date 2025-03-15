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
@Table(name = "EXHIBIT_CATEGORY")
@Entity
public class ExhibitCategoryEntity extends AuditingFields {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exhibit_category_id")
    private Integer exhibitCategoryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_exhibit_category_id")
    private ExhibitCategoryEntity parentExhibitCategory;

    @OneToMany(mappedBy = "parentExhibitCategory")
    @JsonIgnore
    private List<ExhibitCategoryEntity> children;

    @NotNull
    @Column(name = "depth")
    private Integer depth;

    @NotNull
    @Column(length = 15, name = "exhibit_category_name")
    private String exhibitCategoryName;

    @NotNull
    @Column(name = "is_leaf")
    private Boolean isLeaf;
}