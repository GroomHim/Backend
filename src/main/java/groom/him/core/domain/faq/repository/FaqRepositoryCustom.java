package groom.him.core.domain.faq.repository;

import groom.him.core.domain.faq.models.dto.FaqResponse;
import java.util.List;

public interface FaqRepositoryCustom {
    List<FaqResponse> findFaqByIsPublicOrderByPrio();
}