package groom.him.domain.wish.service;

import groom.him.domain.member.models.dto.response.CountResponse;
import groom.him.domain.member.models.entity.MemberEntity;
import groom.him.domain.member.models.entity.WishEntity;
import groom.him.domain.member.service.MemberService;
import groom.him.domain.product.models.dto.response.ProductWithWishResponse;
import groom.him.domain.product.models.entity.ProductEntity;
import groom.him.domain.product.service.ProductService;
import groom.him.domain.wish.exception.WishErrorCode;
import groom.him.domain.wish.exception.WishException;
import groom.him.domain.wish.repository.WishRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class WishService {

    private final MemberService memberService;
    private final ProductService productService;
    private final WishRepository wishRepository;

    public Slice<ProductWithWishResponse> findWishListByMemberId(Integer memberId,
        Boolean isSkinType,
        Pageable pageable) {
        return productService.findMemberProductWishList(memberId, isSkinType,
            pageable);
    }

    public CountResponse findWishCountByMemberId(Integer memberId) {
        return new CountResponse(wishRepository.countDistinctByMember_MemberId(memberId));
    }

    @Transactional
    public void addWish(Integer memberId, Integer productId) {
        MemberEntity member = memberService.findById(memberId);
        ProductEntity product = productService.findById(productId);

        boolean isExisted = wishRepository.existsByMember_MemberIdAndProduct_ProductId(memberId,
            productId);

        if (isExisted) {
            throw new WishException(WishErrorCode.ALREADY_EXISTED_WISH);
        }

        WishEntity entity = new WishEntity(member, product);
        wishRepository.save(entity);
    }

    @Transactional
    public void deleteWish(Integer memberId, Integer productId) {
        wishRepository.deleteByMember_MemberIdAndProduct_ProductId(memberId, productId);
    }
}