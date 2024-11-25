package groom.him.domain.qa.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import groom.him.domain.qa.models.dto.response.QaResponse;
import groom.him.domain.qa.models.entity.QQaAnswerEntity;
import groom.him.domain.qa.models.entity.QQaCategoryEntity;
import groom.him.domain.qa.models.entity.QQaEntity;
import groom.him.domain.qa.models.enums.QaStatus;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class QaRepositoryCustomImpl implements QaRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<QaResponse> findQaByMemberIdAndStatusAndRegDt(Integer memberId, QaStatus qaStatus,
        LocalDate startDate, LocalDate endDate) {
        QQaEntity qa = QQaEntity.qaEntity;
        QQaCategoryEntity qaCategory = QQaCategoryEntity.qaCategoryEntity;
        QQaAnswerEntity qaAnswer = QQaAnswerEntity.qaAnswerEntity;

        BooleanBuilder builder = new BooleanBuilder();

        if (qaStatus != null) {
            builder.and(qa.status.eq(qaStatus));
        }
        if (startDate != null && endDate != null) {
            LocalDateTime startDateTime = startDate.atStartOfDay();
            LocalDateTime endDateTime = endDate.atTime(23, 59, 59);
            builder.and(qa.regDt.between(startDateTime, endDateTime));
        }

        return jpaQueryFactory
            .select(
                Projections.constructor(
                    QaResponse.class,
                    qa.qaId, qaCategory.qaCategoryName, qa.title, qa.content, qaAnswer.answer,
                    qa.status, qa.regDt
                )
            )
            .from(qa)
            .leftJoin(qa.qaCategory, qaCategory)
            .leftJoin(qaAnswer).on(qaAnswer.qa.qaId.eq(qa.qaId))
            .where(builder)
            .orderBy(qa.regDt.desc())
            .fetch();
    }
}