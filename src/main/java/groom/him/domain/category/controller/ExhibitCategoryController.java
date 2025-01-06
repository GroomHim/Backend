package groom.him.domain.category.controller;

import groom.him.core.dto.Response;
import groom.him.domain.category.models.dto.response.ExhibitCategoryResponse;
import groom.him.domain.category.service.ExhibitCategoryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/category")
public class ExhibitCategoryController {
    private final ExhibitCategoryService exhibitCategoryService;

    @GetMapping()
    public Response<List<ExhibitCategoryResponse>> findExhibitCategoryList() {
        return Response.success(exhibitCategoryService.findExhibitCategoryList());
    }
}