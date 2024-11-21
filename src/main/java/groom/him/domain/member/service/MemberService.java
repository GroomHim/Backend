package groom.him.domain.member.service;

import groom.him.domain.product.models.dto.response.ProductBriefResponse;
import groom.him.domain.product.models.entity.ProductEntity;
import groom.him.domain.product.repository.ProductRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    private final ProductRepository productRepository;

    public MemberService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductBriefResponse> findMemberWishList(Integer memberId, Boolean isSkinType) {
        // TODO: 1+N 문제 발생
        List<ProductEntity> productEntityList = productRepository.findMemberWishProductBriefBySkinType(
            memberId, isSkinType);
        return productEntityList.stream().map(product -> ProductBriefResponse.of(product,
                productRepository.findSkinTypeNameListByProductId(product.getProductId())))
            .collect(Collectors.toList());
    }
}