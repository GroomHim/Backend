package groom.him.domain.product.repository;

import groom.him.domain.product.models.entity.ProductEntity;
import java.util.List;

public interface ProductRepositoryCustom {
    List<ProductEntity> findMemberWishProductBriefBySkinType(Integer memberId, Boolean isSkinType);

    List<ProductEntity> findProductListBySkinTypeOrderByQuantity(Integer skinType);
}