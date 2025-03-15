package groom.him.domain.qa.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import groom.him.core.models.entity.RegisterDateFields;
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
@Table(name = "QA_CATEGORY")
@Entity
public class QaCategoryEntity extends RegisterDateFields {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "qa_category_id")
    private Integer qaCategoryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_qa_category_id")
    private QaCategoryEntity parentQaCategory;

    @OneToMany(mappedBy = "parentQaCategory")
    @JsonIgnore
    private List<QaCategoryEntity> children;

    @NotNull
    @Column(length = 15, name = "qa_category_name")
    private String qaCategoryName;

    @NotNull
    @Column(name = "is_leaf")
    private Boolean isLeaf;
}