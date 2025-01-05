package groom.him.domain.order.models.entity;

import groom.him.common.models.entity.RegisterDateFields;
import groom.him.domain.member.models.entity.MemberEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
    private String delivery_request;
}