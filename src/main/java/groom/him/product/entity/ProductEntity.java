package groom.him.product.entity;

import groom.him.core.entity.constant.SkinType;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "PRODUCT")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRODUCT_ID")
    private Long productId;

    @Column(name = "PRODUCT_NAME")
    private String productName;

    @Column(name = "PRICE")
    private BigDecimal price;

    @Column(name = "DISCOUNT_RATE")
    private Double discountRate;

    @Column(name = "DISCOUNTED_PRICE")
    private BigDecimal discountedPrice;

    @ManyToOne
    @JoinColumn(name = "BRAND_ID")
    private BrandEntity brandEntity;

    @Enumerated(EnumType.STRING)
    @Column(name = "SKIN_TYPE")
    private SkinType skinType;
}