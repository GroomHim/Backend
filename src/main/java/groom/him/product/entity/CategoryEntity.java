package groom.him.product.entity;

import groom.him.product.entity.constant.CategoryType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CATEGORY")
public class CategoryEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "CATEGORY_ID")
  private Long categoryId;

  @Enumerated(EnumType.STRING)
  @Column(name = "CATEGORY_TYPE")
  private CategoryType categoryType;

  @Column(name = "DEPTH")
  private int depth;

  @Column(name = "CATEGORY_NM")
  private String categoryName;

  @Column(name = "IS_LEAF")
  private Boolean isLeaf;
}