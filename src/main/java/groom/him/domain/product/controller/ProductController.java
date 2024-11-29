package groom.him.domain.product.controller;

import groom.him.core.dto.Response;
import groom.him.domain.product.models.dto.response.ProductBriefResponse;
import groom.him.domain.product.service.ProductService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/products")
public class ProductController {
    private final ProductService productService;

    @GetMapping("/recommend/random")
    public Response<List<ProductBriefResponse>> findRandomProductBrief() {
        return Response.success(productService.findRandomProductBrief());
    }
}