package groom.him.domain.order.models.entity;

import groom.him.common.models.entity.AuditingFields;
import groom.him.domain.order.models.enums.OrderStatus;
import groom.him.domain.product.models.entity.ProductEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "ORDER_DETAIL")
@Entity
public class OrderDetailEntity extends AuditingFields {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_detail_id")
    private Integer orderDetailId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private OrderEntity order;

    @NotNull
    @Column(name = "price")
    private Integer price;

    @NotNull
    @Column(name = "quantity")
    private Integer quantity;

    @NotNull
    @Column(name = "reward_point")
    private Integer rewardPoint;

    @NotNull
    @Column(name = "order_data")
    private String orderData;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "order_status")
    private OrderStatus orderStatus = OrderStatus.ORDER_COMPLETED;

    public OrderDetailEntity(ProductEntity product, OrderEntity order, Integer price, Integer quantity, Integer rewardPoint, String orderData) {
        this.product = product;
        this.order = order;
        this.price = price;
        this.quantity = quantity;
        this.rewardPoint = rewardPoint;
        this.orderData = orderData;
    }
}