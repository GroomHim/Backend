package groom.him.domain.product.models.dto.request;

import java.util.List;

public record RandomProductRequest(
    List<Integer> categoryIdList
) {
}