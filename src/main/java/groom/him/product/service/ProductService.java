package groom.him.product.service;

import groom.him.core.entity.constant.SkinType;
import groom.him.product.dto.response.ProductResponse;
import groom.him.product.entity.ProductEntity;
import groom.him.product.repository.ProductRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;

    public List<ProductResponse> findProductListBySkinType(SkinType skinType) {
        List<ProductEntity> productBySkinType = productRepository.findBySkinType(skinType);

        return productBySkinType.stream()
            .map(ProductResponse::of)
            .collect(Collectors.toList());
    }
}