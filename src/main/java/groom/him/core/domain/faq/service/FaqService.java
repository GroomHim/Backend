package groom.him.core.domain.faq.service;

import groom.him.core.domain.faq.models.dto.FaqResponse;
import groom.him.core.domain.faq.repository.FaqRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class FaqService {
    private final FaqRepository faqRepository;

    public FaqService(FaqRepository faqRepository) {
        this.faqRepository = faqRepository;
    }

    public List<FaqResponse> getFaqList() {
        return faqRepository.findFaqByIsPublicOrderByPrio();
    }
}