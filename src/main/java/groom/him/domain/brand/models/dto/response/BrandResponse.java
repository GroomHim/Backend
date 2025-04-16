package groom.him.domain.brand.models.dto.response;

import groom.him.domain.brand.models.entity.BrandEntity;

public record BrandResponse(
    Integer brandId,
    String brandName,
    String enBrandName
) {
    public static BrandResponse from(BrandEntity brand) {
        return new BrandResponse(brand.getBrandId(), brand.getBrandName(), brand.getEnBrandName());
    }
}