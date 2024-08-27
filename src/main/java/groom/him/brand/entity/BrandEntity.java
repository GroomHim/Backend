package groom.him.brand.entity;

import groom.him.product.entity.ProductEntity;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "BRND")
public class BrandEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "BRND_ID")
  private Long brndId;

  @Column(name = "BRND_NM")
  private String brndNm;

  @Column(name = "BRND_EN_NM")
  private String brndEnNm;

  @OneToMany(mappedBy = "brand")
  private List<ProductEntity> products;
}
