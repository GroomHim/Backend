package groom.him.domain.product.models.entity;

import groom.him.common.models.entity.AuditingFields;
import groom.him.domain.product.models.enums.ImgType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "PRODUCT_IMG")
@Entity
public class ProductImgEntity extends AuditingFields {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_img_id")
    private Integer productImgId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    @NotNull
    @Column(name = "img_url", length = 2048)
    private String imgUrl;

    @NotNull
    @Column(name = "prio", length = 20)
    private String prio;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", length = 10)
    private ImgType type;

    public ProductImgEntity(ProductEntity product, String imgUrl, String prio, ImgType type) {
        this.product = product;
        this.imgUrl = imgUrl;
        this.prio = prio;
        this.type = type;
    }
}