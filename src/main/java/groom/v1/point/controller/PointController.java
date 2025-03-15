//package groom.v1.point.controller;
//
//import groom.him.core.models.dto.Response;
//import groom.him.domain.member.models.entity.MemberEntity;
//import groom.v1.point.models.dto.response.PointHistoryResponse;
//import groom.v1.point.models.dto.response.PointResponse;
//import groom.v1.point.service.PointService;
//import java.util.List;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/v1/points")
//public class PointController {
//  private final PointService service;
//
//  @GetMapping("/history")
//  public Response<List<PointHistoryResponse>> findPointHistory(@AuthenticationPrincipal MemberEntity member){
//    List<PointHistoryResponse> pointHistory = service.findPointHistory(member.getMemberId());
//    return Response.success(pointHistory);
//  }
//
//  @GetMapping
//  public Response<PointResponse> getPoint(@AuthenticationPrincipal MemberEntity member){
//    PointResponse point = service.getPoint(member.getMemberId());
//    return Response.success(point);
//  }
//}
