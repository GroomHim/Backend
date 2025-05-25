package groom.him.domain.product.service;

import groom.him.domain.category.enums.SortType;
import groom.him.domain.category.repository.ExhibitCategoryRepository;
import groom.him.domain.product.exception.ProductErrorCode;
import groom.him.domain.product.exception.ProductException;
import groom.him.domain.product.models.dto.response.ProductDetailResponse;
import groom.him.domain.product.models.dto.response.ProductWithWishResponse;
import groom.him.domain.product.models.entity.ProductEntity;
import groom.him.domain.product.repository.ProductRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ExhibitCategoryRepository exhibitCategoryRepository;

    public void checkProductExist(Integer productId) {
        if (!productRepository.existsById(productId)) {
            throw new ProductException(ProductErrorCode.PRODUCT_NOT_EXIST);
        }
    }

    public ProductEntity findById(Integer productId) {
        return productRepository.findById(productId).orElseThrow(
            () -> new ProductException(ProductErrorCode.PRODUCT_NOT_EXIST));
    }

    public Slice<ProductWithWishResponse> findRandomProductBrief(Pageable pageable,
        List<Integer> categoryIdList, Integer memberId) {
        List<Integer> subCategoryIdList = exhibitCategoryRepository.getLeafCategoryIdByTargetCategoryId(
            categoryIdList);
        return productRepository.findRandomProductByCategoryId(pageable, subCategoryIdList,
            memberId);
    }

    public Slice<ProductWithWishResponse> findRecommendProductBriefBySkinType(Pageable pageable,
        Integer skinTypeId, Integer memberId) {
        return productRepository.findProductListBySkinType(pageable, skinTypeId,
            memberId);
    }

    public Slice<ProductWithWishResponse> findProductBriefByPrice(Pageable pageable,
        Integer minPrice, Integer maxPrice, Integer memberId) {
        return productRepository.findProductListByPriceRange(pageable, minPrice, maxPrice,
            memberId);
    }

    public ProductDetailResponse findProductDetailByProductId(Integer memberId, Integer productId) {
        return productRepository.findProductDetailByProductId(memberId, productId);
    }

    public Slice<ProductWithWishResponse> findProductListByCategory(Pageable pageable,
        Integer categoryId, SortType sortType, Integer memberId) {
        return productRepository.findProductListByCategoryId(pageable, categoryId, sortType,
            memberId);
    }

    public Slice<ProductWithWishResponse> findMemberProductWishList(Integer memberId,
        Boolean isSkinType, Pageable pageable) {
        return productRepository.findMemberWishProductBriefBySkinType(memberId, isSkinType,
            pageable);
    }

    public Slice<ProductWithWishResponse> findProductListByBrand(Pageable pageable,
        String brandName, Integer memberId) {
        return productRepository.findProductListByBrand(pageable, brandName, memberId);
    }

    public List<ProductWithWishResponse> findProductListByWord(Integer memberId,
        String word, SortType sortType) {
        return productRepository.findProductListBySearchWord(word, memberId, sortType);
    }
}