package groom.him.domain.order.models.dto.request;

public record OrderProductInfo(
    Integer productId,
    Integer price,
    Integer quantity,
    String productImgUrl
) {
}
