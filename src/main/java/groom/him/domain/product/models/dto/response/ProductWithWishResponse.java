package groom.him.domain.product.models.dto.response;

public record ProductWithWishResponse(
    ProductBriefResponse product,
    Boolean isWish
) {
}