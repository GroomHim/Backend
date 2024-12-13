package groom.him.common.service;

import groom.him.common.repository.SkinTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SkinTypeService {
    private final SkinTypeRepository skinTypeRepository;

    public Integer getRandomSkinTypeId() {
        return skinTypeRepository.getRandomSkinTypeId();
    }
}