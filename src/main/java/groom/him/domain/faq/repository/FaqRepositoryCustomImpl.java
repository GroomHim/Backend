package groom.him.domain.faq.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import groom.him.core.common.enums.IsPublic;
import groom.him.domain.faq.models.dto.response.FaqResponse;
import groom.him.core.domain.faq.models.entity.QFaqCategoryEntity;
import groom.him.core.domain.faq.models.entity.QFaqEntity;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FaqRepositoryCustomImpl implements FaqRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<FaqResponse> findFaqByIsPublicOrderByPrio() {
        QFaqEntity faq = QFaqEntity.faqEntity;
        QFaqCategoryEntity faqCategory = QFaqCategoryEntity.faqCategoryEntity;

        return jpaQueryFactory
            .select(
                Projections.constructor(
                    FaqResponse.class,
                    faq.faqId, faqCategory.faqCategoryName, faq.question, faq.answer,
                    faq.prio, faq.isPublic, faq.regDt, faq.updDt
                )
            )
            .from(faq)
            .where(faq.isPublic.eq(IsPublic.OPEN))
            .orderBy(faq.prio.asc())
            .leftJoin(faq.faqCategory, faqCategory)
            .on(faq.faqCategory.faqCategoryId.eq(faqCategory.faqCategoryId))
            .fetch();
    }
}