package groom.him.domain.product.repository;

import groom.him.domain.product.models.entity.BrandEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository extends JpaRepository<BrandEntity, Integer> {
    Boolean existsByEnBrandName(String brandName);
}