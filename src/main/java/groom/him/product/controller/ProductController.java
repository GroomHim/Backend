package groom.him.product.controller;

import groom.him.core.entity.constant.SkinType;
import groom.him.product.dto.response.ProductResponse;
import groom.him.product.service.ProductService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/products")
@RestController
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/member/skinType/list")
    public ResponseEntity<List<ProductResponse>> findProductListBySkinType() {
        var memberSkinType = SkinType.DRY_TYPE_1;
        var response = productService.findProductListBySkinType(memberSkinType);
        return ResponseEntity.ok(response);
    }
}