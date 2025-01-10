package groom.him.domain.point.models.dto.response;

import groom.him.domain.point.models.entity.PointHistoryEntity;
import groom.him.domain.point.models.enums.PointHistoryType;
import java.time.LocalDateTime;

public record PointHistoryResponse (
 Integer point,
 Integer memberId,
 String productName,
 PointHistoryType pointHistoryType,
 LocalDateTime regDt,
 LocalDateTime validFromDt,
 LocalDateTime validToDt,
 Boolean isApplied
){
  public static PointHistoryResponse of(PointHistoryEntity entity){
    return new PointHistoryResponse(
        entity.getPoint(),
        entity.getMember().getMemberId(),
        entity.getProductName(),
        entity.getPointHistoryType(),
        entity.getRegDt(),
        entity.getValidFromDt(),
        entity.getValidToDt(),
        entity.getIsApplied()
    );
  }
}
