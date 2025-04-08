package groom.him.domain.product.repository;

import groom.him.domain.product.models.entity.ProductSkinTypeLinkEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductSkinTypeLinkRepository extends
    JpaRepository<ProductSkinTypeLinkEntity, Integer> {
}