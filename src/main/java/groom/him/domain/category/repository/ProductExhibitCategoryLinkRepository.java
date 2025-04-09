package groom.him.domain.category.repository;

import groom.him.domain.product.models.entity.ProductExhibitCategoryLinkEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductExhibitCategoryLinkRepository extends
    JpaRepository<ProductExhibitCategoryLinkEntity, Integer> {
}