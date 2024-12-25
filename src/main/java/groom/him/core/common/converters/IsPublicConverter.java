package groom.him.core.common.converters;

import groom.him.core.common.enums.IsPublic;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class IsPublicConverter implements AttributeConverter<IsPublic, Boolean> {
    @Override
    public Boolean convertToDatabaseColumn(IsPublic attribute) {
        if(attribute == null) return null;
        return attribute.getCode();
    }

    @Override
    public IsPublic convertToEntityAttribute(Boolean dbData) {
        if (dbData == null) {
            return null;
        }
        return IsPublic.fromCode(dbData);
    }
}