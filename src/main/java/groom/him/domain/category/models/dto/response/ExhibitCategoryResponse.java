package groom.him.domain.category.models.dto.response;

import groom.him.domain.category.models.entity.ExhibitCategoryEntity;
import java.util.ArrayList;
import java.util.List;

public record ExhibitCategoryResponse(
    Integer categoryId,
    String categoryName,
    Boolean hasSubCategory,
    Integer parentCategoryId,
    Integer depth,
    List<ExhibitCategoryResponse> subCategoryList
) {
    public static ExhibitCategoryResponse of(ExhibitCategoryEntity exhibitCategory) {
        List<ExhibitCategoryResponse> subCategoryResponses = new ArrayList<>();
        for (ExhibitCategoryEntity subCategory : exhibitCategory.getChildren()) {
            subCategoryResponses.add(ExhibitCategoryResponse.of(subCategory));
        }

        return new ExhibitCategoryResponse(
            exhibitCategory.getExhibitCategoryId(),
            exhibitCategory.getExhibitCategoryName(),
            !subCategoryResponses.isEmpty(),
            exhibitCategory.getParentExhibitCategory() != null
                ? exhibitCategory.getParentExhibitCategory().getExhibitCategoryId() : null,
            exhibitCategory.getDepth(),
            subCategoryResponses
        );
    }
}