package groom.him.domain.brand.repository;

import groom.him.domain.brand.models.entity.BrandEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository extends JpaRepository<BrandEntity, Integer> {
    Boolean existsByEnBrandName(String brandName);
}