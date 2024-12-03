package groom.him.domain.faq.service;

import groom.him.domain.faq.models.dto.response.FaqResponse;
import groom.him.domain.faq.repository.FaqRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class FaqService {
    private final FaqRepository faqRepository;

    public FaqService(FaqRepository faqRepository) {
        this.faqRepository = faqRepository;
    }

    public List<FaqResponse> findFaqList() {
        return faqRepository.findFaqByIsPublicOrderByPrio();
    }
}