package groom.him.domain.qa.models.dto.response;

import java.util.List;

public record QaCategoryResponse(
    String category,
    List<SubCategory> subCategories
) {

    public static QaCategoryResponse of(String category, List<SubCategory> subCategories) {
        return new QaCategoryResponse(category, subCategories);
    }

    public record SubCategory(
        Integer categoryId,
        String categoryName
    ) {
        public static SubCategory of(Integer categoryId, String categoryName) {
            return new SubCategory(categoryId, categoryName);
        }
    }
}