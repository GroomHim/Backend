package groom.him.domain.skinType.controller;

import groom.him.core.models.dto.Response;
import groom.him.domain.member.models.dto.response.MemberResponse;
import groom.him.domain.member.models.entity.MemberEntity;
import groom.him.domain.member.service.MemberService;
import groom.him.domain.skinType.models.dto.request.ModifyMemberSkinTypeRequest;
import groom.him.domain.skinType.models.dto.response.SkinTypeBriefResponse;
import groom.him.domain.skinType.models.entity.SkinTypeEntity;
import groom.him.domain.skinType.service.SkinTypeService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/skin-types")
public class SkinTypeController {

    private final SkinTypeService skinTypeService;
    private final MemberService memberService;

    @GetMapping("/list")
    public Response<List<SkinTypeBriefResponse>> findSkinTypeList() {
        return Response.success(skinTypeService.findSkinTypeList());
    }

    @PostMapping
    public Response<String> modifyMemberSkinType(
        @AuthenticationPrincipal MemberEntity member,
        @RequestBody ModifyMemberSkinTypeRequest request) {
        SkinTypeEntity skinType = skinTypeService.findByName(request.skinTypeName());
        memberService.modifySkinType(member.getMemberId(), skinType);
        return Response.success(skinType.getDescription());
    }
}