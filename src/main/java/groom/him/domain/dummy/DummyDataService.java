package groom.him.domain.dummy;

import groom.him.domain.category.repository.CategoryRepository;
import groom.him.domain.product.models.entity.ProductEntity;
import groom.him.domain.product.models.entity.ProductImgEntity;
import groom.him.domain.product.models.enums.ImgType;
import groom.him.domain.product.repository.BrandRepository;
import groom.him.domain.product.repository.ProductImageRepository;
import groom.him.domain.product.repository.ProductRepository;
import groom.him.domain.s3.service.S3Service;
import jakarta.transaction.Transactional;
import java.io.IOException;
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

        ProductEntity product = new ProductEntity(
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

        productImageRepository.save(sub1);
        productImageRepository.save(sub2);
        productImageRepository.save(content);
        productRepository.save(product);
    }
}
