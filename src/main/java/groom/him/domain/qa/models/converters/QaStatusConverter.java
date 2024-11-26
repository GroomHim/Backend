package groom.him.domain.qa.models.converters;

import groom.him.core.common.enums.IsPublic;
import groom.him.domain.qa.models.enums.QaStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class QaStatusConverter implements AttributeConverter<QaStatus, String> {
    @Override
    public String convertToDatabaseColumn(QaStatus attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getCode();
    }

    @Override
    public QaStatus convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        return QaStatus.fromCode(dbData);
    }
}