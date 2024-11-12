package groom.him.domain.faq.repository;

import groom.him.domain.faq.models.dto.FaqResponse;
import java.util.List;

public interface FaqRepositoryCustom {
    List<FaqResponse> findFaqByIsPublicOrderByPrio();
}