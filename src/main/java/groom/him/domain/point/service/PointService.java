package groom.him.domain.point.service;

import groom.him.domain.point.exception.PointException;
import groom.him.domain.point.models.dto.response.PointHistoryResponse;
import groom.him.domain.point.models.entity.PointHistoryEntity;
import groom.him.domain.point.models.enums.PointErrorCode;
import groom.him.domain.point.repository.PointRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PointService {
  private final PointRepository repository;

  public List<PointHistoryResponse> findPointHistory(Integer memberId) {
    List<PointHistoryEntity> allByMemberMemberIdTop15 = repository.findTop15ByMemberMemberIdOrderByRegDtDesc(
        memberId).orElseThrow(() -> new PointException(PointErrorCode.POINT_HISTORY_NOT_EXIST));

    List<PointHistoryResponse> responseList = new ArrayList<>();
    allByMemberMemberIdTop15.forEach(each -> {
      PointHistoryResponse response = PointHistoryResponse.of(each);
      responseList.add(response);
    });
    return responseList;
  }
}
