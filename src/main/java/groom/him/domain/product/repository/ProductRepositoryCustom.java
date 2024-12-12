package groom.him.domain.product.repository;

import groom.him.domain.product.models.dto.response.ProductWithWishResponse;
import groom.him.domain.product.models.entity.ProductEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface ProductRepositoryCustom {
    Slice<ProductEntity> findProductListBySkinTypeOrderByQuantity(Pageable pageable,
        Integer skinType);

    Slice<ProductEntity> findProductListByPriceRange(Pageable pageable, Integer minPrice,
        Integer maxPrice);

    Slice<ProductWithWishResponse> findMemberWishProductBriefBySkinType(Integer memberId,
        Boolean isSkinType, Pageable pageable);
}