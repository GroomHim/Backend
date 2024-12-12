package groom.him.domain.product.service;

import groom.him.domain.category.repository.ExhibitCategoryRepository;
import groom.him.domain.product.models.dto.request.RandomProductRequest;
import groom.him.domain.product.models.dto.response.ProductBriefResponse;
import groom.him.domain.product.models.dto.response.ProductWithWishResponse;
import groom.him.domain.product.models.entity.ProductEntity;
import groom.him.domain.product.repository.ProductRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ExhibitCategoryRepository exhibitCategoryRepository;

    public Slice<ProductBriefResponse> findRandomProductBrief(Pageable pageable,
        RandomProductRequest request) {
        List<Integer> subCategoryIdList = exhibitCategoryRepository.getLeafCategoryIdByTargetCategoryId(
            request.categoryIdList());
        List<ProductEntity> productEntityList = productRepository.findRandomProductEntitiesByCategoryId(
            pageable.getPageSize() + 1,
            (int) pageable.getOffset(), subCategoryIdList);

        boolean hasNext = false;
        if (productEntityList.size() > pageable.getPageSize()) {
            hasNext = true;
            productEntityList.removeLast();
        }

        return new SliceImpl<>(productEntityList.stream().map(ProductBriefResponse::of).toList(),
            pageable, hasNext);
    }

    public Slice<ProductWithWishResponse> findRecommendProductBriefBySkinType(Pageable pageable,
        Integer skinTypeId) {
        return productRepository.findProductListBySkinTypeOrderByQuantity(pageable, skinTypeId);
    }

    public Slice<ProductBriefResponse> findProductBriefByPrice(Pageable pageable, Integer minPrice,
        Integer maxPrice) {
        return productRepository.findProductListByPriceRange(pageable, minPrice, maxPrice)
            .map(ProductBriefResponse::of);
    }

}