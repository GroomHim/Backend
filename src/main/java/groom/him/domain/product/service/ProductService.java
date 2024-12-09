package groom.him.domain.product.service;

import groom.him.domain.product.models.dto.response.ProductBriefResponse;
import groom.him.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProductService {
    private final ProductRepository productRepository;

    public List<ProductBriefResponse> findSearchProduct(String word){
        return productRepository.findSearchProduct(word).stream()
                .map(ProductBriefResponse::of).collect(Collectors.toList());

    }

    public List<ProductBriefResponse> findSearchProductIndex(String word){
        return productRepository.findSearchProductIndex(word).stream()
                .map(ProductBriefResponse::of).collect(Collectors.toList());

    }
}
