package groom.him.domain.product.repository;

import groom.him.domain.product.models.entity.ProductImgEntity;
import groom.him.domain.product.models.enums.ImgType;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImgEntity, Integer> {
    void deleteAllByProduct_ProductId(Integer productId);

    List<ProductImgEntity> findByProduct_ProductIdAndType(Integer productId, ImgType type);
}