package groom.him.category.entity;

import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "CATEGORY")
public class CategoryEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "CATEGORY_ID")
  private Long categoryId;

  @Column(name = "CATEGORY_TYPE")
  private String categoryType;

  @Column(name = "DEPTH")
  private int depth;

  @Column(name = "CATEGORY_NM")
  private String categoryName;

  @Column(name = "IS_LEAF")
  private Boolean isLeaf;

  @Column(name = "REG_DT")
  private LocalDateTime regDt;

  @Column(name = "UDT_DT")
  private LocalDateTime udtDt;

  @OneToMany(mappedBy = "category")
  private List<ProductCategoryLinkEntity> productCategoryLinks;
}
