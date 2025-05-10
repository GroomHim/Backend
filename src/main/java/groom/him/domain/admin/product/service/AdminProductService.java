package groom.him.domain.admin.product.service;

import groom.him.core.common.enums.IsPublic;
import groom.him.core.models.constant.SkinTypeErrorCode;
import groom.him.core.s3.service.S3Service;
import groom.him.domain.admin.product.models.dto.request.CreateProductRequest;
import groom.him.domain.admin.product.models.dto.request.ModifyProductImageRequest;
import groom.him.domain.admin.product.models.dto.request.ModifyProductRequest;
import groom.him.domain.brand.exception.BrandErrorCode;
import groom.him.domain.brand.exception.BrandException;
import groom.him.domain.brand.models.entity.BrandEntity;
import groom.him.domain.brand.repository.BrandRepository;
import groom.him.domain.category.enums.CategoryErrorCode;
import groom.him.domain.category.exception.ExhibitCategoryException;
import groom.him.domain.category.models.entity.CategoryEntity;
import groom.him.domain.category.models.entity.ExhibitCategoryEntity;
import groom.him.domain.category.repository.CategoryRepository;
import groom.him.domain.category.repository.ExhibitCategoryRepository;
import groom.him.domain.category.repository.ProductExhibitCategoryLinkRepository;
import groom.him.domain.product.exception.ProductErrorCode;
import groom.him.domain.product.exception.ProductException;
import groom.him.domain.product.models.dto.response.ProductBriefResponse;
import groom.him.domain.product.models.dto.response.ProductDetailResponse;
import groom.him.domain.product.models.dto.response.ProductResponse;
import groom.him.domain.product.models.entity.ProductEntity;
import groom.him.domain.product.models.entity.ProductExhibitCategoryLinkEntity;
import groom.him.domain.product.models.entity.ProductImgEntity;
import groom.him.domain.product.models.entity.ProductSkinTypeLinkEntity;
import groom.him.domain.product.models.enums.ImgType;
import groom.him.domain.product.repository.ProductImageRepository;
import groom.him.domain.product.repository.ProductRepository;
import groom.him.domain.product.repository.ProductSkinTypeLinkRepository;
import groom.him.domain.skinType.exception.SkinTypeException;
import groom.him.domain.skinType.models.entity.SkinTypeEntity;
import groom.him.domain.skinType.repository.SkinTypeRepository;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
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
    private final SkinTypeRepository skinTypeRepository;
    private final ProductSkinTypeLinkRepository productSkinTypeLinkRepository;

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

        // 5. 상품 스킨타입 맵핑
        saveProductSkinTypeLink(request.skinType(), savedProduct);
    }

    private void saveProductSkinTypeLink(List<Integer> skinTypeList, ProductEntity savedProduct) {
        List<ProductSkinTypeLinkEntity> productSkinTypeLinkEntityList = skinTypeList.stream()
            .map((skinTypeId) -> {
                SkinTypeEntity skinType = skinTypeRepository.findById(skinTypeId).orElseThrow(
                    () -> new SkinTypeException(SkinTypeErrorCode.SKIN_TYPE_NOT_EXIST));
                return ProductSkinTypeLinkEntity.from(skinType, savedProduct);
            }).toList();
        productSkinTypeLinkRepository.saveAll(productSkinTypeLinkEntityList);
    }


    private void saveProductImages(ProductEntity savedProduct, List<String> mainImageUrlList,
        ImgType imgType) {
        List<ProductImgEntity> productImgEntityList = new ArrayList<>();
        AtomicInteger priority = new AtomicInteger(1);
        mainImageUrlList.forEach((imgUrl) -> {
            ProductImgEntity productImgEntity = ProductImgEntity.from(savedProduct, imgUrl,
                String.valueOf(priority.getAndIncrement()), imgType); // TODO: prio 처리
            productImgEntityList.add(productImgEntity);
        });
        productImageRepository.saveAll(productImgEntityList);
    }

    @Transactional
    public void changeProductState(Integer productId) {
        ProductEntity product = getAvailableProductEntity(productId);
        product.changeState(
            product.getIsPublic().equals(IsPublic.OPEN) ? IsPublic.CLOSE : IsPublic.OPEN
        );
    }

    @Transactional
    public void deleteProduct(Integer productId) {
        ProductEntity product = getAvailableProductEntity(productId);
        if (product.getIsPublic().equals(IsPublic.OPEN)) {
            throw new ProductException(ProductErrorCode.PRODUCT_CANNOT_DELETE);
        }
        product.softDelete();
    }

    @Transactional
    public void modifyProduct(ModifyProductRequest request, Integer productId) {
        ProductEntity product = getAvailableProductEntity(productId);
        BrandEntity newBrand = product.getBrand();
        CategoryEntity newCategory = product.getCategory();

        if (request.brandId() != null && !product.getBrand().getBrandId()
            .equals(request.brandId())) {
            newBrand = brandRepository.findById(request.brandId())
                .orElseThrow(() -> new BrandException(BrandErrorCode.BRAND_NOT_EXIST));
        }

        if (!product.getCategory().getCategoryId().equals(request.categoryId())) {
            newCategory = categoryRepository.findById(request.categoryId())
                .orElseThrow(
                    () -> new ExhibitCategoryException(CategoryErrorCode.CATEGORY_NOT_EXIST));
        }

        if (request.skinType() != null) {
            productSkinTypeLinkRepository.deleteAllById(request.skinType());
            saveProductSkinTypeLink(request.skinType(), product);
        }

        product.modifyProduct(request, newCategory, newBrand);
    }

    @Transactional
    public void modifyProductImage(ModifyProductImageRequest request, Integer productId) {
        ProductEntity product = getAvailableProductEntity(productId);

        productImageRepository.deleteAllByProduct_ProductId(productId);

        // 상품 대표 이미지 설정
        List<String> mainImageUrlList = s3Service.uploadProductImages(request.mainImage(),
            productId, ImgType.MAIN);
        product.setImgUrl(mainImageUrlList.get(0));
        saveProductImages(product, mainImageUrlList, ImgType.MAIN);

        List<String> contentImageUrlList = s3Service.uploadProductImages(request.contentImage(),
            productId, ImgType.CONTENT);
        saveProductImages(product, contentImageUrlList, ImgType.CONTENT);
    }

    private ProductEntity getAvailableProductEntity(Integer productId) {
        return productRepository.findByProductIdAndIsDeletedFalse(productId)
            .orElseThrow(() -> new ProductException(ProductErrorCode.PRODUCT_NOT_EXIST));
    }

    public Slice<ProductBriefResponse> getProducts(IsPublic isPublic, Pageable pageable) {
        return productRepository.findByIsPublicAndIsDeletedFalseOrderByRegDt(isPublic, pageable)
            .map(ProductBriefResponse::of);
    }

    public ProductDetailResponse getDetailProduct(Integer productId) {
        ProductEntity product = getAvailableProductEntity(productId);
        ProductResponse productResponse = ProductResponse.of(product);

        List<String> mainImage = getImgsByProductIdAndType(productId, ImgType.MAIN);
        List<String> contentImage = getImgsByProductIdAndType(productId, ImgType.CONTENT);

        return new ProductDetailResponse(productResponse, false, mainImage, contentImage);
    }

    private List<String> getImgsByProductIdAndType(Integer productId, ImgType type) {
        return productImageRepository.findByProduct_ProductIdAndType(
                productId, type)
            .stream().map(ProductImgEntity::getImgUrl)
            .toList();
    }
}
