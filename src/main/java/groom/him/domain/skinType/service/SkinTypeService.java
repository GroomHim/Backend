package groom.him.domain.skinType.service;

import groom.him.domain.skinType.exception.SkinTypeException;
import groom.him.core.models.constant.SkinTypeErrorCode;
import groom.him.domain.skinType.models.dto.response.SkinTypeBriefResponse;
import groom.him.domain.skinType.models.entity.SkinTypeEntity;
import groom.him.domain.skinType.repository.SkinTypeRepository;
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

    public SkinTypeEntity findById(Integer skinTypeId) {
        return skinTypeRepository.findById(skinTypeId)
            .orElseThrow(() -> new SkinTypeException(SkinTypeErrorCode.SKIN_TYPE_NOT_EXIST));
    }
}