package groom.him.domain.admin.product.service;

import groom.him.domain.admin.product.models.dto.response.CreateProductRequest;
import groom.him.domain.category.enums.CategoryErrorCode;
import groom.him.domain.category.exception.ExhibitCategoryException;
import groom.him.domain.category.models.entity.CategoryEntity;
import groom.him.domain.category.models.entity.ExhibitCategoryEntity;
import groom.him.domain.category.repository.CategoryRepository;
import groom.him.domain.category.repository.ExhibitCategoryRepository;
import groom.him.domain.category.repository.ProductExhibitCategoryLinkRepository;
import groom.him.domain.product.exception.BrandErrorCode;
import groom.him.domain.product.exception.BrandException;
import groom.him.domain.product.models.entity.BrandEntity;
import groom.him.domain.product.models.entity.ProductEntity;
import groom.him.domain.product.models.entity.ProductExhibitCategoryLinkEntity;
import groom.him.domain.product.repository.BrandRepository;
import groom.him.domain.product.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminProductService {
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;
    private final ExhibitCategoryRepository exhibitCategoryRepository;
    private final ProductRepository productRepository;
    private final ProductExhibitCategoryLinkRepository productExhibitCategoryLinkRepository;

    @Transactional
    public void addProduct(CreateProductRequest request) {
        BrandEntity brand = brandRepository.findById(request.brandId())
            .orElseThrow(() -> new BrandException(BrandErrorCode.BRAND_NOT_EXIST));
        CategoryEntity category = categoryRepository.findById(request.categoryId())
            .orElseThrow(() -> new ExhibitCategoryException(CategoryErrorCode.CATEGORY_NOT_EXIST));
        ExhibitCategoryEntity exhibitCategory = exhibitCategoryRepository.findById(
                request.categoryId())
            .orElseThrow(() -> new ExhibitCategoryException(CategoryErrorCode.CATEGORY_NOT_EXIST));

        // 1. 상품 기본 정보 저장
        ProductEntity product = ProductEntity.from(request, brand, category);
        ProductEntity savedProduct = productRepository.save(product);

        // 2. 메인 사진 업로드 & 상품 Entity 메인 사진 저장

        // 3. 컨텐츠 사진 업로드

        // 4. 전시 카테고리 맵핑
        ProductExhibitCategoryLinkEntity linkEntity = ProductExhibitCategoryLinkEntity.from(
            exhibitCategory, savedProduct);
        productExhibitCategoryLinkRepository.save(linkEntity);
    }
}