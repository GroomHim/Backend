package groom.him.domain.product.models.entity;

import groom.him.common.models.entity.AuditingFields;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "BRAND")
@Entity
public class BrandEntity extends AuditingFields {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "brand_id")
    private Integer brandId;

    @NotNull
    @Column(name = "brand_name")
    String brandName;

    @NotNull
    @Column(name = "en_brand_name")
    String enBrandName;

    public BrandEntity(String brandName) {
        this.brandName = brandName;
    }
}