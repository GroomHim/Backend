package groom.him.domain.cart.models.entity;

import groom.him.common.models.entity.RegisterDateFields;
import groom.him.domain.member.models.entity.MemberEntity;
import groom.him.domain.product.models.entity.ProductEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "CART")
@Entity
public class CartEntity extends RegisterDateFields {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Integer cartId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private MemberEntity member;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity product;

    @NotNull
    @Column(name = "count")
    private Integer count = 1;

    public CartEntity(MemberEntity member, ProductEntity product) {
        this.member = member;
        this.product = product;
    }

    public void increaseCount() {
        this.count++;
    }

    public void decreaseCount() {
        this.count--;
    }
}