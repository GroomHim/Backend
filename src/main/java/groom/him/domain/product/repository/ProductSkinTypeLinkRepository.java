package groom.him.domain.product.repository;

import groom.him.domain.product.models.entity.ProductSkinTypeLinkEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductSkinTypeLinkRepository extends
    JpaRepository<ProductSkinTypeLinkEntity, Integer> {
    @Query("SELECT pstl.skinType.skinTypeId FROM ProductSkinTypeLinkEntity pstl WHERE pstl.product.productId = :productId")
    List<Integer> findSkinTypeIdsByProductId(@Param("productId") Integer productId);
}