package groom.him.product.entity;

import groom.him.brand.entity.BrandEntity;
import groom.him.category.entity.ProductCategoryLinkEntity;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "PRODUCT")
public class ProductEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "PRODUCT_ID")
  private Long productId;

  @Column(name = "REG_DT")
  private LocalDateTime regDt;

  @Column(name = "UDT_DT")
  private LocalDateTime udtDt;

  @Column(name = "PRODUCT_NAME")
  private String productName;

  @Column(name = "PRICE")
  private Double price;

  @Column(name = "DISCOUNT_RATE")
  private Double discountRate;

  @Column(name = "DISCOUNTED_PRICE")
  private Double discountedPrice;

  @ManyToOne
  @JoinColumn(name = "BRND_ID")
  private BrandEntity brandEntity;

  @Column(name = "SKIN_TYPE")
  private String skinType;

  @OneToMany(mappedBy = "product")
  private List<ProductImageEntity> productImages;

  @OneToMany(mappedBy = "product")
  private List<ProductSkinTypeLinkEntity> skinTypeLinks;

  @OneToMany(mappedBy = "product")
  private List<ProductCategoryLinkEntity> categoryLinks;
}

