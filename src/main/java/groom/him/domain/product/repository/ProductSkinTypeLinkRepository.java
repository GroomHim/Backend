package groom.him.domain.product.repository;

import groom.him.domain.product.models.entity.ProductExhibitCategoryLinkEntity;
import groom.him.domain.product.models.entity.ProductSkinTypeLinkEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductSkinTypeLinkRepository extends JpaRepository<ProductSkinTypeLinkEntity, Integer> {
}
