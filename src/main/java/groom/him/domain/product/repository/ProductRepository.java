package groom.him.domain.product.repository;

import groom.him.domain.product.models.entity.ProductEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Integer>,
    ProductRepositoryCustom {
    @Query(value = """
           SELECT p.*
           FROM PRODUCT p
           LEFT JOIN PRODUCT_EXHIBIT_CATEGORY_LINK pecl on p.product_id = pecl.product_id
           WHERE pecl.exhibit_category_id IN :target
           ORDER BY rand()
           LIMIT :size
           OFFSET :offset;
        """, nativeQuery = true)
    List<ProductEntity> findRandomProductEntitiesByCategoryId(
        @Param("size") int size,
        @Param("offset") int offset,
        @Param("target") List<Integer> target);
}