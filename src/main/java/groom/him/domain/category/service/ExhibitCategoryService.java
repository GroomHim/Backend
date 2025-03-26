package groom.him.domain.category.service;

import groom.him.domain.category.enums.CategoryErrorCode;
import groom.him.domain.category.exception.ExhibitCategoryException;
import groom.him.domain.category.models.dto.response.ExhibitCategoryResponse;
import groom.him.domain.category.repository.ExhibitCategoryRepository;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExhibitCategoryService {
    private final ExhibitCategoryRepository exhibitCategoryRepository;

    @Transactional
    public List<ExhibitCategoryResponse> findExhibitCategoryList() {
        List<ExhibitCategoryResponse> result = new ArrayList<>();
        exhibitCategoryRepository.findAllByParentExhibitCategoryIsNull()
            .forEach(exhibitCategory -> result.add(ExhibitCategoryResponse.of(exhibitCategory)));
        return result;
    }

    public void checkExhibitCategoryIsLeaf(Integer categoryId) {
        if(!exhibitCategoryRepository.findById(categoryId).orElseThrow(() -> new ExhibitCategoryException(CategoryErrorCode.CATEGORY_NOT_EXIST)).getIsLeaf()) {
            throw new ExhibitCategoryException(CategoryErrorCode.CATEGORY_IS_NOT_LEAF);
        }
    }
}