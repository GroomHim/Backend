package groom.him.domain.product.repository;

import groom.him.domain.product.models.entity.ProductImgEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImgEntity, Integer> {
}