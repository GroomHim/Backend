package groom.him.domain.product.service;

import groom.him.domain.category.repository.ExhibitCategoryRepository;
import groom.him.domain.product.models.dto.response.ProductBriefResponse;
import groom.him.domain.product.models.entity.ProductEntity;
import groom.him.domain.product.repository.ProductRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ExhibitCategoryRepository exhibitCategoryRepository;

    public List<ProductBriefResponse> findRandomProductBrief() {
        // TODO: '얼굴', '바디'에 해당하는 값을 하드코딩으로 넣을지? FE에서 request로 넘겨줄지?
        List<Integer> target = List.of(1, 2);
        List<Integer> subCategoryIdList = exhibitCategoryRepository.getLeafCategoryIdByTargetCategoryId(
            target);
        List<ProductEntity> productEntityList = productRepository.findRandomProductEntitiesByCategoryId(
            subCategoryIdList);
        return productEntityList.stream().map(ProductBriefResponse::of).toList();
    }
}