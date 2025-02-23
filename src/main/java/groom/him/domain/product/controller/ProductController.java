package groom.him.domain.product.controller;

import groom.him.common.service.SkinTypeService;
import groom.him.core.dto.Response;
import groom.him.domain.category.enums.SortType;
import groom.him.domain.category.service.ExhibitCategoryService;
import groom.him.domain.member.models.entity.MemberEntity;
import groom.him.domain.product.models.dto.response.ProductBriefResponse;
import groom.him.domain.product.models.dto.response.ProductDetailResponse;
import groom.him.domain.product.models.dto.response.ProductWithWishResponse;
import groom.him.domain.product.service.ProductService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/products")
public class ProductController {
    private final ProductService productService;
    private final SkinTypeService skinTypeService;
    private final ExhibitCategoryService exhibitCategoryService;

    @GetMapping("/recommend/random")
    public Response<Slice<ProductWithWishResponse>> findRandomProductBrief(Pageable pageable,
        @AuthenticationPrincipal MemberEntity member,
        @RequestParam(value = "categoryId") List<Integer> categoryIdList) {
        return Response.success(productService.findRandomProductBrief(pageable, categoryIdList,
            member.getMemberId()));
    }

    @GetMapping("/recommend/skin-type")
    public Response<Slice<ProductWithWishResponse>> findRecommendProductBriefBySkinType(
        @AuthenticationPrincipal MemberEntity member,
        Pageable pageable
    ) {
        Integer skinTypeId = member.getSkinTypeEntity() == null ?
            skinTypeService.getRandomSkinTypeId() : member.getSkinTypeEntity().getSkinTypeId();
        return Response.success(
            productService.findRecommendProductBriefBySkinType(pageable, skinTypeId,
                member.getMemberId()));
    }

    @GetMapping("/price")
    public Response<Slice<ProductWithWishResponse>> findProductBriefByCost(
        @AuthenticationPrincipal MemberEntity member,
        @RequestParam(value = "min-price") Integer minPrice,
        @RequestParam(value = "max-price") Integer maxPrice,
        Pageable pageable
    ) {
        return Response.success(
            productService.findProductBriefByPrice(pageable, minPrice, maxPrice,
                member.getMemberId()));
    }

    @PostMapping("/search")
    public Response<List<ProductWithWishResponse>> findSearchProductIndex(
        @AuthenticationPrincipal MemberEntity member, @RequestParam String word) {
        return Response.success(productService.findSearchProductIndex(word, member));
    }

    @GetMapping("/{product-id}/detail")
    public Response<ProductDetailResponse> findProductDetail(
        @AuthenticationPrincipal MemberEntity member,
        @PathVariable("product-id") Integer productId) {
        return Response.success(
            productService.findProductDetailByProductId(member.getMemberId(), productId));
    }

    @GetMapping()
    public Response<Slice<ProductWithWishResponse>> findProductListByCategory(
        @AuthenticationPrincipal MemberEntity member,
        @RequestParam(value = "category") Integer categoryId,
        @RequestParam(value = "sort", required = false) SortType sortType,
        Pageable pageable
    ) {
        exhibitCategoryService.checkExhibitCategoryIsLeaf(categoryId);

        sortType = sortType == null ? SortType.SALE : sortType;

        return Response.success(
            productService.findProductListByCategory(pageable, categoryId, sortType,
                member.getMemberId()));
    }
}