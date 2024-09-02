package groom.him.product.entity;

import groom.him.core.entity.AuditingFields;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "PRODUCT_IMG")
public class ProductImageEntity extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRODUCT_IMG_ID")
    private Long productImgId;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private ProductEntity productEntity;

    @Column(name = "IMG_URL")
    private String imgUrl;
}