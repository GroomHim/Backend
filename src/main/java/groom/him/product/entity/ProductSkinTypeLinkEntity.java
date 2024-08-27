package groom.him.product.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "PRODUCT_SKIN_TYPE_LINK")
public class ProductSkinTypeLinkEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "SKIN_TYPE_LINK_ID")
  private Long skinTypeLinkId;

  @ManyToOne
  @JoinColumn(name = "PRODUCT_ID")
  private ProductEntity productEntity;

  @Column(name = "SKIN_TYPE")
  private String skinType;


}
