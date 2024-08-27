package groom.him.category.entity;

import groom.him.product.entity.ProductEntity;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "PRODUCT_CATEGORY_LINK")
public class ProductCategoryLinkEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY) // 단일 ID 생성
  @Column(name = "ID")
  private Long id;

  @ManyToOne
  @JoinColumn(name = "PRODUCT_ID")
  private ProductEntity productEntity;

  @ManyToOne
  @JoinColumn(name = "CATEGORY_ID")
  private CategoryEntity categoryEntity;

  @Column(name = "REG_DT")
  private LocalDateTime regDt;

  @Column(name = "UDT_DT")
  private LocalDateTime udtDt;

}
