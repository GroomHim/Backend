package groom.him.common.service;

import groom.him.common.exception.SkinTypeException;
import groom.him.common.models.constant.SkinTypeErrorCode;
import groom.him.common.models.dto.response.SkinTypeBriefResponse;
import groom.him.common.models.entity.SkinTypeEntity;
import groom.him.common.repository.SkinTypeRepository;
import groom.him.core.model.member.exception.MemberErrorCode;
import groom.him.core.model.member.exception.MemberException;
import groom.him.domain.member.models.entity.MemberEntity;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SkinTypeService {
    private final SkinTypeRepository skinTypeRepository;

    public Integer getRandomSkinTypeId() {
        return skinTypeRepository.getRandomSkinTypeId();
    }

    public List<SkinTypeBriefResponse> findSkinTypeList() {
        List<SkinTypeBriefResponse> result = new ArrayList<>();
        skinTypeRepository.findAll().stream()
            .map(SkinTypeBriefResponse::of).forEach(result::add);
        return result;
    }

    public SkinTypeEntity findBySkinTypeId(Integer skinTypeId) {
        return skinTypeRepository.findById(skinTypeId)
            .orElseThrow(() -> new SkinTypeException(SkinTypeErrorCode.SKIN_TYPE_NOT_EXIST));
    }
}