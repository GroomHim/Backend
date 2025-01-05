package groom.him.domain.order.models.dto.request;

import com.mysema.commons.lang.Pair;
import groom.him.domain.member.models.entity.MemberEntity;
import groom.him.domain.order.models.entity.OrderEntity;

import java.util.List;

public record AddOrderRequest(
    String orderId,
    List<OrderProductInfo> products,
    Integer usedPoint,
    String receiverName,
    String receiverPhoneNumber,
    String receiverAddress,
    String receiverAddressDetail,
    String deliveryRequest
) {

    public static OrderEntity of(AddOrderRequest request, MemberEntity member) {
        Pair<Integer, Integer> totalPriceAndQuantity = getTotalPriceAndQuantity(request.products);

        return new OrderEntity(
            request.orderId,
            member,
            totalPriceAndQuantity.getFirst(),
            totalPriceAndQuantity.getSecond(),
            request.receiverName,
            request.receiverPhoneNumber,
            request.receiverAddress,
            request.receiverAddressDetail,
            request.deliveryRequest
        );
    }

    private static Pair<Integer, Integer> getTotalPriceAndQuantity(List<OrderProductInfo> products) {
        int totalPrice = 0;
        int totalQuantity = 0;

        for (OrderProductInfo product : products) {
            totalPrice += product.price();
            totalQuantity += product.quantity();
        }

        return new Pair<>(totalPrice, totalQuantity);
    }
}
