package groom.him.domain.product.models.entity;

import groom.him.common.models.entity.AuditingFields;
import groom.him.domain.category.models.entity.CategoryEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "PRODUCT")
@Entity
public class ProductEntity extends AuditingFields {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Integer productId;

    // TODO: BRAND Entity 매핑
    @Column(name = "brand_id")
    private Integer brandId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private CategoryEntity category;

    @NotNull
    @Column(name = "product_name", length = 50)
    private String productName;

    @NotNull
    @Column(name = "price")
    private Integer price;

    @NotNull
    @Column(name = "discount_rate")
    private Float discountRate;

    @NotNull
    @Column(name = "discounted_price")
    private Integer discountedPrice;

    @Column(name = "ingredients", columnDefinition = "TEXT")
    private String ingredients;

    @NotNull
    @Column(name = "img_url", length = 2048)
    private String imgUrl;
}