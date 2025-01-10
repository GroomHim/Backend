package groom.him.domain.point.service;

import groom.him.core.model.member.exception.MemberErrorCode;
import groom.him.core.model.member.exception.MemberException;
import groom.him.core.model.member.repository.MemberRepository;
import groom.him.domain.member.models.entity.MemberEntity;
import groom.him.domain.member.service.MemberService;
import groom.him.domain.point.exception.PointException;
import groom.him.domain.point.models.dto.response.PointHistoryResponse;
import groom.him.domain.point.models.dto.response.PointResponse;
import groom.him.domain.point.models.entity.PointHistoryEntity;
import groom.him.domain.point.models.enums.PointErrorCode;
import groom.him.domain.point.models.enums.PointHistoryType;
import groom.him.domain.point.repository.PointRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PointService {
  private final PointRepository pointRepository;
  private final MemberService memberService;

  public List<PointHistoryResponse> findPointHistory(final Integer memberId) {
    List<PointHistoryEntity> allByMemberMemberIdTop15 = pointRepository.findTop15ByMemberMemberIdOrderByRegDtDesc(
        memberId).orElseThrow(() -> new PointException(PointErrorCode.POINT_HISTORY_NOT_EXIST));

    List<PointHistoryResponse> responseList = new ArrayList<>();
    allByMemberMemberIdTop15.forEach(each -> {
      PointHistoryResponse response = PointHistoryResponse.of(each);
      responseList.add(response);
    });
    return responseList;
  }

  @Transactional(readOnly = true)
  public PointResponse getPoint(final Integer memberId){
    MemberEntity member = memberService.findById(memberId);
    final List<PointHistoryEntity> pointHistory = pointRepository
        .findImmutableByMemberMemberIdAndPointHistoryTypeEquals(memberId, PointHistoryType.SAVE)
        .orElseThrow(
            () -> new PointException(PointErrorCode.POINT_HISTORY_NOT_EXIST)
        );

    final Long[] points = calPoint(pointHistory);
    return new PointResponse(member.getPoint(), points[0], points[1]);
  }


  public Long[] calPoint(final List<PointHistoryEntity> list) {
    synchronized (list) {
      AtomicLong tbdPoint = new AtomicLong(0L); // 적립 예정
      AtomicLong tbePoint = new AtomicLong(0L); // 소멸 예정

      list.forEach(each -> {
        if (!each.getIsApplied()) { // isApplied가 false일 때
          tbdPoint.addAndGet(each.getPoint());
        }
        if (each.getValidToDt().isBefore(LocalDateTime.now().plusDays(30))) {
          tbePoint.addAndGet(each.getPoint());
        }
      });
      return new Long[]{tbdPoint.get(), tbePoint.get()};
    }
  }
}
