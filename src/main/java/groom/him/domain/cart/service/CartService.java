package groom.him.domain.cart.service;

import groom.him.domain.cart.exception.CartErrorCode;
import groom.him.domain.cart.exception.CartException;
import groom.him.domain.cart.models.dto.response.CartResponse;
import groom.him.domain.cart.models.dto.response.CartsResponse;
import groom.him.domain.cart.models.entity.CartEntity;
import groom.him.domain.cart.repository.CartRepository;
import groom.him.domain.member.models.entity.MemberEntity;
import groom.him.domain.member.service.MemberService;
import groom.him.domain.product.models.dto.response.ProductBriefResponse;
import groom.him.domain.product.models.entity.ProductEntity;
import groom.him.domain.product.service.ProductService;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CartService {

    private final CartRepository cartRepository;
    private final MemberService memberService;
    private final ProductService productService;

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

    @Transactional
    public void deleteCart(Integer memberId, Integer cartId) {
        CartEntity entity = findById(cartId);
        validateMemberOfCart(memberId, entity);
        cartRepository.delete(entity);
    }

    @Transactional
    public CartResponse increaseCount(Integer memberId, Integer cartId) {
        CartEntity entity = findById(cartId);
        validateMemberOfCart(memberId, entity);

        entity.increaseCount();
        return CartResponse.from(entity);
    }

    @Transactional
    public CartResponse decreaseCount(Integer memberId, Integer cartId) {
        CartEntity entity = findById(cartId);
        validateMemberOfCart(memberId, entity);

        if (entity.getCount() == 0) {
            throw new CartException(CartErrorCode.CART_NOT_DECREASE_PRODUCT_COUNT);
        }

        entity.decreaseCount();
        return CartResponse.from(entity);
    }

    private CartEntity findById(Integer cartId) {
        return cartRepository.findById(cartId).orElseThrow(
            () -> new CartException(CartErrorCode.CART_NOT_EXIST)
        );
    }

    private void validateMemberOfCart(Integer memberId, CartEntity entity) {
        MemberEntity cartMember = entity.getMember();

        if (!Objects.equals(cartMember.getMemberId(), memberId)) {
            throw new CartException(CartErrorCode.CART_UNAUTHORIZED);
        }
    }
}