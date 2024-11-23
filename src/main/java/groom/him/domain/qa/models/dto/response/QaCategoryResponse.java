package groom.him.domain.qa.models.dto.response;

import java.util.List;

public record QaCategoryResponse(
    String category,
    List<String> subCategories
) {
    
    public static QaCategoryResponse of(String category, List<String> subCategories) {
        return new QaCategoryResponse(category, subCategories);
    }
}