package groom.him.common.service;

import groom.him.common.models.dto.response.SkinTypeBriefResponse;
import groom.him.common.repository.SkinTypeRepository;
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
}