package groom.him.domain.admin.product.controller;

import groom.him.core.models.dto.Response;
import groom.him.domain.admin.product.models.dto.request.CreateProductRequest;
import groom.him.domain.admin.product.models.dto.request.ModifyProductImageRequest;
import groom.him.domain.admin.product.models.dto.request.ModifyProductRequest;
import groom.him.domain.admin.product.service.AdminProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/v1/products")
public class AdminProductController {

    private final AdminProductService adminProductService;

    @PostMapping()
    public Response<Integer> addProduct(@ModelAttribute CreateProductRequest request) {
        adminProductService.addProduct(request);
        return new Response<>(HttpStatus.CREATED.value());
    }

    @PatchMapping("/{productId}/status")
    public Response<Integer> changeProductState(@PathVariable Integer productId) {
        adminProductService.changeProductState(productId);
        return new Response<>(HttpStatus.OK.value());
    }

    @DeleteMapping("/{productId}")
    public Response<Integer> deleteProduct(@PathVariable Integer productId) {
        adminProductService.deleteProduct(productId);
        return new Response<>(HttpStatus.NO_CONTENT.value());
    }

    @PatchMapping("/{productId}")
    public Response<Integer> modifyProduct(@RequestBody ModifyProductRequest request,
        @PathVariable Integer productId) {
        adminProductService.modifyProduct(request, productId);
        return new Response<>(HttpStatus.OK.value());
    }

    @PatchMapping("/{productId}/image")
    public Response<Integer> modifyProductImage(@ModelAttribute ModifyProductImageRequest request,
        @PathVariable Integer productId) {
        adminProductService.modifyProductImage(request, productId);
        return new Response<>(HttpStatus.OK.value());
    }

}