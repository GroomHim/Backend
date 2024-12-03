package groom.him.domain.product.repository;

import groom.him.domain.product.models.entity.ProductEntity;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface ProductRepositoryCustom {
    List<ProductEntity> findMemberWishProductBriefBySkinType(Integer memberId, Boolean isSkinType);

    Slice<ProductEntity> findProductListBySkinTypeOrderByQuantity(Pageable pageable,
        Integer skinType);

    List<ProductEntity> findProductListByPriceRange(Integer minPrice, Integer maxPrice);
}