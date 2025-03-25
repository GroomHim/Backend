package groom.him.domain.product.models.entity;

import groom.him.core.common.converters.IsPublicConverter;
import groom.him.core.common.enums.IsPublic;
import groom.him.core.models.entity.AuditingFields;
import groom.him.domain.admin.product.models.dto.request.CreateProductRequest;
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

    @Column(name = "img_url", length = 2048)
    private String imgUrl;

    @Column(name = "delivery_info", length = 50)
    private String deliveryInfo;

    @Column(name = "purchase_site_url", length = 2048)
    private String purchaseSiteUrl;

    @Column(name = "is_public", length = 1)
    @NotNull
    @Convert(converter = IsPublicConverter.class)
    private IsPublic isPublic = IsPublic.CLOSE;

    @Column(name = "is_deleted")
    @NotNull
    private Boolean isDeleted = false;

    protected ProductEntity(String productName, Integer price, Float discountRate,
        Integer discountedPrice, String ingredients, String imgUrl, String deliveryInfo,
        String purchaseSiteUrl, BrandEntity brand, CategoryEntity category) {
        this.productName = productName;
        this.price = price;
        this.discountRate = discountRate;
        this.discountedPrice = discountedPrice;
        this.ingredients = ingredients;
        this.imgUrl = imgUrl;
        this.deliveryInfo = deliveryInfo;
        this.purchaseSiteUrl = purchaseSiteUrl;
        this.brand = brand;
        this.category = category;
    }

    public static ProductEntity from(CreateProductRequest request, BrandEntity brand,
        CategoryEntity category, String imgUrl) {
        return new ProductEntity(
            request.productName(),
            request.price(),
            request.discountRate(),
            request.discountedPrice(),
            request.ingredients(),
            imgUrl,
            request.deliveryInfo(),
            request.purchaseSiteUrl(),
            brand,
            category
        );
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public void changeState(IsPublic isPublic) {
        this.isPublic = isPublic;
    }

    public void softDelete() {
        this.isDeleted = true;
    }
}