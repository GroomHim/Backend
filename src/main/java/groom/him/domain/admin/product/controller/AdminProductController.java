package groom.him.domain.admin.product.controller;

import groom.him.core.models.dto.Response;
import groom.him.domain.admin.product.models.dto.request.CreateProductRequest;
import groom.him.domain.admin.product.service.AdminProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/v1/products")
public class AdminProductController {
    private final AdminProductService adminProductService;

    @PostMapping()
    public Response<Void> addProduct(@ModelAttribute CreateProductRequest request) {
        adminProductService.addProduct(request);
        return new Response<>(HttpStatus.CREATED.value());
    }
}