package groom.him.domain.cart.service;

import groom.him.domain.cart.models.entity.CartEntity;
import groom.him.domain.cart.repository.CartRepository;
import groom.him.domain.member.models.entity.MemberEntity;
import groom.him.domain.member.service.MemberService;
import groom.him.domain.product.models.entity.ProductEntity;
import groom.him.domain.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CartService {

    private final CartRepository cartRepository;
    private final MemberService memberService;
    private final ProductService productService;

    public void addCart(Integer memberId, Integer productId) {
        MemberEntity member = memberService.findMemberById(memberId);
        ProductEntity product = productService.findProductById(productId);

        CartEntity entity = new CartEntity(member, product);
        cartRepository.save(entity);
    }
}