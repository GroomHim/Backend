package groom.him.domain.dummy;

import groom.him.common.models.entity.SkinTypeEntity;
import groom.him.common.repository.SkinTypeRepository;
import groom.him.domain.category.repository.CategoryRepository;
import groom.him.domain.category.repository.ExhibitCategoryRepository;
import groom.him.domain.product.models.entity.ProductEntity;
import groom.him.domain.product.models.entity.ProductExhibitCategoryLinkEntity;
import groom.him.domain.product.models.entity.ProductImgEntity;
import groom.him.domain.product.models.entity.ProductSkinTypeLinkEntity;
import groom.him.domain.product.models.enums.ImgType;
import groom.him.domain.product.repository.BrandRepository;
import groom.him.domain.product.repository.ProductExhibitCategoryLinkRepository;
import groom.him.domain.product.repository.ProductImageRepository;
import groom.him.domain.product.repository.ProductRepository;
import groom.him.domain.product.repository.ProductSkinTypeLinkRepository;
import groom.him.domain.s3.service.S3Service;
import jakarta.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DummyDataService {

    private final S3Service s3Service;
    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;
    private final ProductExhibitCategoryLinkRepository productExhibitCategoryLinkRepository;

    private final ProductSkinTypeLinkRepository productSkinTypeLinkRepository;
    private final ExhibitCategoryRepository exhibitCategoryRepository;
    private final SkinTypeRepository skinTypeRepository;

    @Transactional
    public void createDummyData(
        Integer memberId,
        CreateDummyDataRequest request
    ) throws IOException {
        var brand = brandRepository.findByBrandName(request.brandName()).get();
        var category = categoryRepository.findByCategoryName(request.categoryName()).get();

        String mainImage = s3Service.saveImage(request.mainImage(), memberId, "main");
        String subImage1 = s3Service.saveImage(request.subImage1(), memberId, "sub");
        String subImage2 = s3Service.saveImage(request.subImage2(), memberId, "sub");
        String contentImage = s3Service.saveImage(request.contentImage(), memberId, "content");

        int discountedPrice = (int) (request.price() * (1 - request.discountRate()));

        var product = new ProductEntity(
            brand,
            category,
            request.productName(),
            request.price(),
            request.discountRate(),
            discountedPrice,
            null,
            mainImage,
            request.deliveryInfo()
        );

        ProductImgEntity sub1 = new ProductImgEntity(
            product,
            subImage1,
            "1",
            ImgType.MAIN
        );

        ProductImgEntity sub2 = new ProductImgEntity(
            product,
            subImage2,
            "2",
            ImgType.MAIN
        );

        ProductImgEntity content = new ProductImgEntity(
            product,
            contentImage,
            "2",
            ImgType.CONTENT
        );

        // exhibitCategoryEntity 찾기
        var exhibitCategoryEntity = exhibitCategoryRepository.findById(category.getCategoryId())
            .get();

        // product store
        var savedProduct = productRepository.save(product);

        productImageRepository.save(sub1);
        productImageRepository.save(sub2);
        productImageRepository.save(content);

        // exhibitCategoryLink 테이블에 연결
        productExhibitCategoryLinkRepository.save(
            new ProductExhibitCategoryLinkEntity(
                exhibitCategoryEntity,
                savedProduct
            )
        );

        Integer randomSkinTypeId = skinTypeRepository.getRandomSkinTypeId() % 11;
        SkinTypeEntity skinType1 = skinTypeRepository.findById(12).get();
        SkinTypeEntity skinType2 = skinTypeRepository.findById(randomSkinTypeId).get();

        // 랜덤한 skinType에 productSkinType 연동
        productSkinTypeLinkRepository.saveAll(
            List.of(
                new ProductSkinTypeLinkEntity(
                    savedProduct,
                    skinType1
                ),
                new ProductSkinTypeLinkEntity(
                    savedProduct,
                    skinType2
                )
            )
        );
    }
}
