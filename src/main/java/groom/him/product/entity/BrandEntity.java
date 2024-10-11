package groom.him.product.entity;

import groom.him.core.entity.AuditingFields;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "BRAND")
public class BrandEntity extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BRAND_ID")
    private Long brandId;

    @Column(name = "BRAND_NM")
    private String brandNm;

    @Column(name = "BRAND_EN_NM")
    private String brandEnNm;
}