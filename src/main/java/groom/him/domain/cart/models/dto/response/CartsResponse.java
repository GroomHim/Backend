package groom.him.domain.cart.models.dto.response;

import groom.him.domain.product.models.dto.response.ProductBriefResponse;

public record CartsResponse(
    ProductBriefResponse product,
    Integer count
) {
}