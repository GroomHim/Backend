package groom.him.domain.product.controller;

import groom.him.common.service.SkinTypeService;
import groom.him.core.dto.Response;
import groom.him.domain.member.models.entity.MemberEntity;
import groom.him.domain.product.models.dto.response.ProductBriefResponse;
import groom.him.domain.product.service.ProductService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
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
    public Response<List<ProductBriefResponse>> findRandomProductBrief() {
        return Response.success(productService.findRandomProductBrief());
    }

    @GetMapping("/recommend/skin-type")
    public Response<List<ProductBriefResponse>> findRecommendProductBriefBySkinType(
        @AuthenticationPrincipal MemberEntity member
    ) {
        Integer skinTypeId;
        if (member.getSkinTypeEntity() == null) {
            skinTypeId = skinTypeService.getRandomSkinTypeId();
        } else {
            skinTypeId = member.getSkinTypeEntity().getSkinTypeId();
        }
        return Response.success(productService.findRecommendProductBriefBySkinType(skinTypeId));
    }

    @GetMapping("/price")
    public Response<List<ProductBriefResponse>> findProductBriefByCost(
        @RequestParam(value = "min-price") Integer minPrice,
        @RequestParam(value = "max-price") Integer maxPrice
    ) {
        return Response.success(productService.findProductBriefByPrice(minPrice, maxPrice));
    }
}