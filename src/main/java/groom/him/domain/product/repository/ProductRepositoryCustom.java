package groom.him.domain.product.repository;

import groom.him.domain.product.models.entity.ProductEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface ProductRepositoryCustom {
    Slice<ProductEntity> findMemberWishProductBriefBySkinType(Integer memberId, Boolean isSkinType,
        Pageable pageable);
}