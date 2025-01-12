package groom.him.common.models.dto.response;

import groom.him.common.models.entity.SkinTypeEntity;

public record SkinTypeBriefResponse(
    Integer skinTypeId,
    String skinTypeName,
    String description
) {
    public static SkinTypeBriefResponse of(SkinTypeEntity skinType) {
        return new SkinTypeBriefResponse(skinType.getSkinTypeId(), skinType.getSkinTypeName(),
            skinType.getDescription());
    }
}