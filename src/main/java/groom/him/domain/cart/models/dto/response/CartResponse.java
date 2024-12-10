package groom.him.domain.cart.models.dto.response;

import groom.him.domain.cart.models.entity.CartEntity;

public record CartResponse(
    Integer cartId,
    Integer memberId,
    Integer productId,
    Integer count
) {
    public static CartResponse from(CartEntity entity) {
        return new CartResponse(
            entity.getCartId(),
            entity.getMember().getMemberId(),
            entity.getProduct().getProductId(),
            entity.getCount()
        );
    }
}