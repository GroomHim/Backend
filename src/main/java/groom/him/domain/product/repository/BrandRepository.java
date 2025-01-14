package groom.him.domain.product.repository;

import groom.him.domain.product.models.entity.BrandEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<BrandEntity, Integer> {
    Optional<BrandEntity> findByBrandName(String brandName);
}
