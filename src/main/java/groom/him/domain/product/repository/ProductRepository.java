package groom.him.domain.product.repository;

import groom.him.domain.product.models.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Integer>,
    ProductRepositoryCustom {
}