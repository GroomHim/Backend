package groom.him.domain.brand.controller;

import groom.him.core.models.dto.Response;
import groom.him.domain.brand.models.dto.response.BrandResponse;
import groom.him.domain.brand.service.BrandService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/brands")
public class BrandController {
    private final BrandService brandService;

    @GetMapping()
    public Response<List<BrandResponse>> findBrandList() {
        return Response.success(brandService.findBrandList());
    }
}