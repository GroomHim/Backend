package groom.him.domain.cart.models.dto;

import groom.him.domain.product.models.dto.response.ProductBriefResponse;

public record CartsResponse(
    ProductBriefResponse product,
    Integer count
) {
}