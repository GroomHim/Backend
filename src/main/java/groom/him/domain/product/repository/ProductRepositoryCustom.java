package groom.him.domain.product.repository;

import groom.him.domain.product.models.dto.response.ProductWithWishResponse;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface ProductRepositoryCustom {
    Slice<ProductWithWishResponse> findProductListBySkinTypeOrderByQuantity(Pageable pageable,
        Integer skinType);

    Slice<ProductWithWishResponse> findProductListByPriceRange(Pageable pageable, Integer minPrice,
        Integer maxPrice);

    Slice<ProductWithWishResponse> findMemberWishProductBriefBySkinType(Integer memberId,
        Boolean isSkinType, Pageable pageable);

    Slice<ProductWithWishResponse> findRandomProductByCategoryId(Pageable pageable,
        List<Integer> target);
}