package groom.him.domain.product.controller;

import groom.him.common.service.SkinTypeService;
import groom.him.core.dto.Response;
import groom.him.domain.member.models.entity.MemberEntity;
import groom.him.domain.product.models.dto.request.RandomProductRequest;
import groom.him.domain.product.models.dto.response.ProductBriefResponse;
import groom.him.domain.product.models.dto.response.ProductWithWishResponse;
import groom.him.domain.product.service.ProductService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/products")
public class ProductController {
    private final ProductService productService;
    private final SkinTypeService skinTypeService;

    @GetMapping("/recommend/random")
    public Response<Slice<ProductWithWishResponse>> findRandomProductBrief(Pageable pageable,
        @RequestBody RandomProductRequest request) {
        return Response.success(productService.findRandomProductBrief(pageable, request));
    }

    @GetMapping("/recommend/skin-type")
    public Response<Slice<ProductWithWishResponse>> findRecommendProductBriefBySkinType(
        @AuthenticationPrincipal MemberEntity member,
        Pageable pageable
    ) {
        Integer skinTypeId = member.getSkinTypeEntity() == null ?
            skinTypeService.getRandomSkinTypeId() : member.getSkinTypeEntity().getSkinTypeId();
        return Response.success(
            productService.findRecommendProductBriefBySkinType(pageable, skinTypeId));
    }

    @GetMapping("/price")
    public Response<Slice<ProductWithWishResponse>> findProductBriefByCost(
        @RequestParam(value = "min-price") Integer minPrice,
        @RequestParam(value = "max-price") Integer maxPrice,
        Pageable pageable
    ) {
        return Response.success(
            productService.findProductBriefByPrice(pageable, minPrice, maxPrice));
    }
}