//package groom.v1.point.service;
//
//import groom.him.domain.member.models.entity.MemberEntity;
//import groom.him.domain.member.service.MemberService;
//import groom.v1.point.exception.PointException;
//import groom.v1.point.models.dto.response.PointHistoryResponse;
//import groom.v1.point.models.dto.response.PointResponse;
//import groom.v1.point.models.entity.PointHistoryEntity;
//import groom.v1.point.models.enums.PointErrorCode;
//import groom.v1.point.models.enums.PointHistoryType;
//import groom.v1.point.repository.PointRepository;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.atomic.AtomicInteger;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//@Service
//@RequiredArgsConstructor
//public class PointService {
//  private final PointRepository pointRepository;
//  private final MemberService memberService;
//
//  public List<PointHistoryResponse> findPointHistory(final Integer memberId) {
//    List<PointHistoryEntity> allByMemberMemberIdTop15 = pointRepository.findTop15ByMemberMemberIdOrderByRegDtDesc(
//        memberId).orElseThrow(() -> new PointException(PointErrorCode.POINT_HISTORY_NOT_EXIST));
//
//    List<PointHistoryResponse> responseList = new ArrayList<>();
//    allByMemberMemberIdTop15.forEach(each -> {
//      PointHistoryResponse response = PointHistoryResponse.of(each);
//      responseList.add(response);
//    });
//    return responseList;
//  }
//
//  @Transactional(readOnly = true)
//  public PointResponse getPoint(final Integer memberId){
//    MemberEntity member = memberService.findById(memberId);
//    final List<PointHistoryEntity> pointHistory = pointRepository
//        .findImmutableByMemberMemberIdAndPointHistoryTypeEquals(memberId, PointHistoryType.SAVE)
//        .orElseThrow(
//            () -> new PointException(PointErrorCode.POINT_HISTORY_NOT_EXIST)
//        );
//
//    final Integer[] points = calPoint(pointHistory);
//    return new PointResponse(member.getPoint(), points[0], points[1]);
//  }
//
//
//  public Integer[] calPoint(final List<PointHistoryEntity> list) {
//    synchronized (list) {
//      AtomicInteger tbdPoint = new AtomicInteger(0);
//      AtomicInteger tbePoint = new AtomicInteger(0);
//
//      list.forEach(each -> {
//        if (!each.getIsApplied()) {
//          tbdPoint.addAndGet(each.getPoint());
//        }
//        if (each.getValidToDt().isBefore(LocalDateTime.now().plusDays(30))) {
//          tbePoint.addAndGet(each.getPoint());
//        }
//      });
//      return new Integer[]{tbdPoint.get(), tbePoint.get()};
//    }
//  }
//}
