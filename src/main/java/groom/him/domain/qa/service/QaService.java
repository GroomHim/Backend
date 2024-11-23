package groom.him.domain.qa.service;

import groom.him.domain.qa.models.dto.response.QaCategoryResponse;
import groom.him.domain.qa.models.entity.QaCategoryEntity;
import groom.him.domain.qa.repository.QaCategoryRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class QaService {
    private final QaCategoryRepository qaCategoryRepository;

    public QaService(QaCategoryRepository qaCategoryRepository) {
        this.qaCategoryRepository = qaCategoryRepository;
    }

    public List<QaCategoryResponse> findQaCategoryList() {
        return qaCategoryRepository.findAllByParentQaCategoryIsNull().stream()
            .map(category -> QaCategoryResponse.of(
                category.getQaCategoryName(),
                category.getChildren().stream()
                    .map(QaCategoryEntity::getQaCategoryName)
                    .collect(Collectors.toList()))
            ).collect(Collectors.toList());
    }
}