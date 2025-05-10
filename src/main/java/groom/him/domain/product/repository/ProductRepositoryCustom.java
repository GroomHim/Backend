package groom.him.domain.product.repository;

import groom.him.domain.category.enums.SortType;
import groom.him.domain.product.models.dto.response.ProductDetailResponse;
import groom.him.domain.product.models.dto.response.ProductWithWishResponse;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface ProductRepositoryCustom {

    Slice<ProductWithWishResponse> findProductListBySkinType(Pageable pageable,
        Integer skinType, Integer memberId);

    Slice<ProductWithWishResponse> findProductListByPriceRange(Pageable pageable, Integer minPrice,
        Integer maxPrice, Integer memberId);

    Slice<ProductWithWishResponse> findMemberWishProductBriefBySkinType(Integer memberId,
        Boolean isSkinType, Pageable pageable);

    Slice<ProductWithWishResponse> findRandomProductByCategoryId(Pageable pageable,
        List<Integer> target, Integer memberId);

    ProductDetailResponse findProductDetailByProductId(Integer memberId, Integer productId);

    Slice<ProductWithWishResponse> findProductListByCategoryId(Pageable pageable,
        Integer categoryId, SortType sortType, Integer memberId);

    Slice<ProductWithWishResponse> findProductListByBrand(Pageable pageable, String brandName,
        Integer memberId);

    List<ProductWithWishResponse> findProductListBySearchWord(String word,
        Integer memberId);
}