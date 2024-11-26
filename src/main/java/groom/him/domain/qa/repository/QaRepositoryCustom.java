package groom.him.domain.qa.repository;

import groom.him.domain.qa.models.dto.response.QaResponse;
import groom.him.domain.qa.models.enums.QaStatus;
import java.time.LocalDate;
import java.util.List;

public interface QaRepositoryCustom {
    List<QaResponse> findQaByMemberIdAndStatusAndRegDt(Integer memberId, QaStatus qaStatus,
        LocalDate startDate, LocalDate endDate);
}