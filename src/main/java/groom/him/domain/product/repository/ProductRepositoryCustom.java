package groom.him.domain.product.repository;

import groom.him.domain.product.models.entity.ProductEntity;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepositoryCustom {
    List<ProductEntity> findMemberWishProductBriefBySkinType(Integer memberId, Boolean isSkinType);

    List<ProductEntity> findSearchProduct(String word);
}