package groom.him.domain.product.repository;

import groom.him.domain.product.models.entity.ProductEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Integer>,
    ProductRepositoryCustom {
    @Query(value = "SELECT * FROM PRODUCT WHERE MATCH(product_name) AGAINST(?1 IN BOOLEAN MODE) ORDER BY reg_dt DESC", nativeQuery = true)
    List<ProductEntity> findSearchProductIndex(String word);
}