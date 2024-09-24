package groom.him.product.repository;

import groom.him.core.entity.constant.SkinType;
import groom.him.product.entity.ProductEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    List<ProductEntity> findBySkinType(SkinType skinType);
}
