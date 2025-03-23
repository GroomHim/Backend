package groom.him.domain.product.models.entity;

import groom.him.core.models.entity.RegisterDateFields;
import groom.him.domain.category.models.entity.ExhibitCategoryEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "PRODUCT_EXHIBIT_CATEGORY_LINK")
@Entity
public class ProductExhibitCategoryLinkEntity extends RegisterDateFields {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_exhibit_category_id")
    private Integer productExhibitCategoryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exhibit_category_id", nullable = false)
    private ExhibitCategoryEntity exhibitCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity product;

    protected ProductExhibitCategoryLinkEntity(ExhibitCategoryEntity exhibitCategory,
        ProductEntity product) {
        this.exhibitCategory = exhibitCategory;
        this.product = product;
    }

    public static ProductExhibitCategoryLinkEntity from(ExhibitCategoryEntity exhibitCategory,
        ProductEntity product) {
        return new ProductExhibitCategoryLinkEntity(exhibitCategory, product);
    }
}