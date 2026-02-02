package groom.him.domain.product.service;

import groom.him.core.s3.exception.S3Exception;
import groom.him.core.s3.models.dto.request.UploadCompleteRequest;
import groom.him.core.s3.models.dto.request.UploadCompleteRequest.UploadedImageInfo;
import groom.him.core.s3.models.enums.S3ErrorCode;
import groom.him.domain.product.exception.ProductErrorCode;
import groom.him.domain.product.exception.ProductException;
import groom.him.domain.product.models.entity.ProductEntity;
import groom.him.domain.product.models.entity.ProductImgEntity;
import groom.him.domain.product.repository.ProductImageRepository;
import groom.him.domain.product.repository.ProductRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductImgService {
    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;
    private static final String PRODUCT_IMAGE_PREFIX = "products/";


    @Transactional
    public void addUploadedProductImages(Integer productId, List<UploadedImageInfo> images) {
        ProductEntity product = productRepository.findById(productId)
            .orElseThrow(() -> new ProductException(ProductErrorCode.PRODUCT_NOT_EXIST));

        String expectedPrefix = PRODUCT_IMAGE_PREFIX + productId + "/";

        List<ProductImgEntity> productImgEntityList = new ArrayList<>();

        AtomicInteger priority = new AtomicInteger(1);
        for (UploadCompleteRequest.UploadedImageInfo info : images) {
            validateKeyPrefix(info.key(), expectedPrefix);
            ProductImgEntity productImgEntity = ProductImgEntity.from(product, info.key(),
                String.valueOf(priority.getAndIncrement()), info.type());
            productImgEntityList.add(productImgEntity);
        }
        productImageRepository.saveAll(productImgEntityList);
    }

    private void validateKeyPrefix(String key, String expectedPrefix) {
        if (!key.startsWith(expectedPrefix)) {
            throw new S3Exception(S3ErrorCode.INVALID_PRESIGNED_URL);
        }
    }
}