package groom.him.common.controller;

import groom.him.common.models.dto.response.SkinTypeBriefResponse;
import groom.him.common.service.SkinTypeService;
import groom.him.core.dto.Response;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/skin-types")
public class SkinTypeController {
    private final SkinTypeService skinTypeService;

    @GetMapping("/list")
    public Response<List<SkinTypeBriefResponse>> findSkinTypeList() {
        return Response.success(skinTypeService.findSkinTypeList());
    }
}