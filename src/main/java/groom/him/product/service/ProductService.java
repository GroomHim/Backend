package groom.him.product.service;

import groom.him.core.entity.constant.SkinType;
import groom.him.product.dto.response.ProductResponseDto;
import groom.him.product.entity.ProductEntity;
import groom.him.product.repository.ProductRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductResponseDto> findProductListBySkinType(SkinType skinType) {
        List<ProductEntity> productBySkinType = productRepository.findBySkinType(skinType);

        return productBySkinType.stream()
            .map(ProductResponseDto::of)
            .collect(Collectors.toList());
    }
}