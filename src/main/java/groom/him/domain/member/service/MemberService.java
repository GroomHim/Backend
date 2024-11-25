package groom.him.domain.member.service;

import groom.him.domain.product.models.dto.response.ProductBriefResponse;
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
        return productRepository.findMemberWishProductBriefBySkinType(memberId, isSkinType).stream()
            .map(ProductBriefResponse::of).collect(Collectors.toList());
    }
}