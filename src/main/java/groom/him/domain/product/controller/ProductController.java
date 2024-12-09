package groom.him.domain.product.controller;

import groom.him.core.dto.Response;
import groom.him.domain.product.models.dto.response.ProductBriefResponse;
import groom.him.domain.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/products")
public class ProductController {
    private final ProductService searchService;

    @GetMapping("/search")
    public Response<List<ProductBriefResponse>> findSearchProductIndex(@RequestParam String word){
        return Response.success(searchService.findSearchProductIndex(word));
    }
}
