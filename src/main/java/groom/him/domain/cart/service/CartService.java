package groom.him.domain.cart.service;

import groom.him.domain.cart.models.dto.CartResponse;
import groom.him.domain.cart.models.dto.CartsResponse;
import groom.him.domain.cart.models.entity.CartEntity;
import groom.him.domain.cart.repository.CartRepository;
import groom.him.domain.member.models.entity.MemberEntity;
import groom.him.domain.member.service.MemberService;
import groom.him.domain.product.models.dto.response.ProductBriefResponse;
import groom.him.domain.product.models.entity.ProductEntity;
import groom.him.domain.product.service.ProductService;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CartService {

    private final CartRepository cartRepository;
    private final MemberService memberService;
    private final ProductService productService;

    @Transactional
    public List<CartsResponse> findCarts(Integer memberId) {
        MemberEntity member = memberService.findMemberById(memberId);

        return cartRepository.findAllByMemberOrderByRegDt(member).stream()
            .map(cart -> new CartsResponse(
                ProductBriefResponse.of(cart.getProduct()),
                cart.getCount()))
            .toList();
    }

    @Transactional
    public CartResponse saveCart(Integer memberId, Integer productId) {
        MemberEntity member = memberService.findMemberById(memberId);
        ProductEntity product = productService.findProductById(productId);

        Optional<CartEntity> cartOptional = cartRepository.findByMemberAndProduct(member, product);

        if (cartOptional.isPresent()) {
            CartEntity cart = cartOptional.get();
            cart.increaseCount();
            return CartResponse.from(cart);
        }

        CartEntity newCart = new CartEntity(member, product);
        CartEntity savedCart = cartRepository.save(newCart);
        return CartResponse.from(savedCart);
    }
}