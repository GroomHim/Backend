package groom.him.domain.admin.product.service;

import groom.him.core.s3.service.S3Service;
import groom.him.domain.admin.product.models.dto.request.CreateProductRequest;
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
import groom.him.domain.product.models.entity.ProductImgEntity;
import groom.him.domain.product.models.enums.ImgType;
import groom.him.domain.product.repository.BrandRepository;
import groom.him.domain.product.repository.ProductImageRepository;
import groom.him.domain.product.repository.ProductRepository;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
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
    private final ProductImageRepository productImageRepository;
    private final S3Service s3Service;

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
        ProductEntity product = ProductEntity.from(request, brand, category, null);
        ProductEntity savedProduct = productRepository.save(product);

        // 2. 메인 사진 업로드 & 상품 Entity 메인 사진 저장
        List<String> mainImageUrlList = s3Service.uploadProductImages(request.mainImage(),
            savedProduct.getProductId(), ImgType.MAIN);

        savedProduct.setImgUrl(mainImageUrlList.get(0));
        saveProductImages(savedProduct, mainImageUrlList, ImgType.MAIN);

        // 3. 컨텐츠 사진 업로드
        List<String> contentImageUrlList = s3Service.uploadProductImages(request.contentImage(),
            savedProduct.getProductId(), ImgType.CONTENT);
        saveProductImages(savedProduct, contentImageUrlList, ImgType.CONTENT);

        // 4. 전시 카테고리 맵핑
        ProductExhibitCategoryLinkEntity linkEntity = ProductExhibitCategoryLinkEntity.from(
            exhibitCategory, savedProduct);
        productExhibitCategoryLinkRepository.save(linkEntity);
    }


    private void saveProductImages(ProductEntity savedProduct, List<String> mainImageUrlList,
        ImgType imgType) {
        List<ProductImgEntity> productImgEntityList = new ArrayList<>();
        mainImageUrlList.forEach((imgUrl) -> {
            ProductImgEntity productImgEntity = ProductImgEntity.from(savedProduct, imgUrl, "a",
                imgType); // TODO: prio 처리
            productImgEntityList.add(productImgEntity);
        });
        productImageRepository.saveAll(productImgEntityList);
    }
}