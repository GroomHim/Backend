package groom.him.domain.product.models.entity;

import groom.him.common.models.entity.AuditingFields;
import groom.him.domain.category.models.entity.CategoryEntity;
import jakarta.persistence.*;
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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "brand_id")
    @NotNull
    private BrandEntity brand;

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

    @NotNull
    @Column(name = "delivery_info", length = 50)
    private String deliveryInfo;

    public ProductEntity(BrandEntity brand, CategoryEntity category, String productName,
        Integer price,
        Float discountRate, Integer discountedPrice, String ingredients, String imgUrl,
        String deliveryInfo) {
        this.brand = brand;
        this.category = category;
        this.productName = productName;
        this.price = price;
        this.discountRate = discountRate;
        this.discountedPrice = discountedPrice;
        this.ingredients = ingredients;
        this.imgUrl = imgUrl;
        this.deliveryInfo = deliveryInfo;
    }
}