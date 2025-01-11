package groom.him.domain.order.models.entity;

import groom.him.common.models.entity.RegisterDateFields;
import groom.him.domain.member.models.entity.MemberEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "ORDERS")
@Entity
public class OrderEntity extends RegisterDateFields {
    @Id
    @Column(name = "order_id")
    private String orderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity member;

    @NotNull
    @Column(name = "total_price")
    private Integer totalPrice;

    @NotNull
    @Column(name = "total_quantity")
    private Integer totalQuantity;

    @NotNull
    @Column(name = "receiver_name")
    private String receiverName;

    @NotNull
    @Column(name = "receiver_phone_number")
    private String receiverPhoneNumber;

    @NotNull
    @Column(name = "receiver_address")
    private String receiverAddress;

    @NotNull
    @Column(name = "receiver_address_detail")
    private String receiverAddressDetail;

    @NotNull
    @Column(name = "delivery_request")
    private String deliveryRequest;

    public OrderEntity(String orderId, MemberEntity member, Integer totalPrice, Integer totalQuantity, String receiverName,
                       String receiverPhoneNumber, String receiverAddress, String receiverAddressDetail, String deliveryRequest) {
        this.orderId = orderId;
        this.member = member;
        this.totalPrice = totalPrice;
        this.totalQuantity = totalQuantity;
        this.receiverName = receiverName;
        this.receiverPhoneNumber = receiverPhoneNumber;
        this.receiverAddress = receiverAddress;
        this.receiverAddressDetail = receiverAddressDetail;
        this.deliveryRequest = deliveryRequest;
    }
}