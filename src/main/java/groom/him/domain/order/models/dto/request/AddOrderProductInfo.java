package groom.him.domain.order.models.dto.request;

public record AddOrderProductInfo(
    Integer productId,
    Integer price,
    Integer quantity,
    String productImgUrl
) {
}
