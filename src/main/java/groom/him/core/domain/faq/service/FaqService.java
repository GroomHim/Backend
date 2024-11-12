package groom.him.core.domain.faq.service;

import groom.him.core.common.enums.IsPublic;
import groom.him.core.domain.faq.models.dto.FaqResponse;
import groom.him.core.domain.faq.models.entity.FaqEntity;
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
        List<FaqEntity> result = faqRepository.findAllByIsPublicOrderByPrioAsc(IsPublic.OPEN);
        return result.stream()
            .map(faq -> new FaqResponse(
                faq.getFaqId(), faq.getFaqCategoryId(), faq.getQuestion(), faq.getAnswer(),
                faq.getPrio(), faq.getIsPublic(), faq.getRegDt(), faq.getUpdDt()
                ))
            .toList();
    }
}