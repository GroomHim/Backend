package groom.him.domain.cart.models.dto.request;

public record ModifyCartCountRequest(
    Integer cartId,
    Integer count
) {
}