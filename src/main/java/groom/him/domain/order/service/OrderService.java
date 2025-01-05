package groom.him.domain.order.service;

import groom.him.domain.member.models.entity.MemberEntity;
import groom.him.domain.member.service.MemberService;
import groom.him.domain.order.exception.OrderErrorCode;
import groom.him.domain.order.exception.OrderException;
import groom.him.domain.order.models.dto.request.AddOrderProductInfo;
import groom.him.domain.order.models.dto.request.AddOrderRequest;
import groom.him.domain.order.models.dto.request.OrderProductInfo;
import groom.him.domain.order.models.dto.response.OrderBriefResponse;
import groom.him.domain.order.models.entity.OrderDetailEntity;
import groom.him.domain.order.models.entity.OrderEntity;
import groom.him.domain.order.repository.OrderDetailRepository;
import groom.him.domain.order.repository.OrderRepository;
import groom.him.domain.product.models.entity.ProductEntity;
import groom.him.domain.product.service.ProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@RequiredArgsConstructor
@Service
public class OrderService {
    private static final String ORDER_NUMBER_PREFIX = "OD";
    private static final double REWORD_RATE = 0.01;
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");
    private static final ConcurrentHashMap<String, AtomicInteger> orderNumberInDays = new ConcurrentHashMap<>();

    private final MemberService memberService;
    private final ProductService productService;
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;

    public String getOrderId() {
        String today = getTodayDate();
        AtomicInteger getOrderNumber = orderNumberInDays.computeIfAbsent(today, key -> new AtomicInteger(0));
        String orderNumber = String.format("%06d", getOrderNumber.incrementAndGet());
        return ORDER_NUMBER_PREFIX + today + orderNumber;
    }

    private String getTodayDate() {
        LocalDate currentDate = LocalDate.now();
        return currentDate.format(DATE_TIME_FORMATTER);
    }

    public OrderBriefResponse findOrderBrief(String orderId) {
        OrderEntity order = findOrder(orderId);
        List<OrderDetailEntity> orderDetails = orderDetailRepository.findByOrder_OrderId(order.getOrderId());

        List<OrderProductInfo> productInfos = new ArrayList<>();

        orderDetails.forEach(entity -> {
            OrderProductInfo info = OrderProductInfo.from(entity);
            productInfos.add(info);
        });

        return new OrderBriefResponse(order.getOrderId(), productInfos);
    }

    public List<OrderBriefResponse> findMemberOrderBriefs(Integer memberId) {
        List<OrderEntity> orders = orderRepository.findByMember_memberIdOrderByOrderIdDesc(memberId);

        List<OrderBriefResponse> response = new ArrayList<>();

        orders.forEach(order -> {
            OrderBriefResponse orderBrief = findOrderBrief(order.getOrderId());
            response.add(orderBrief);
        });

        return response;
    }

    public OrderEntity findOrder(String orderId) {
        return orderRepository.findById(orderId).orElseThrow(
            () -> new OrderException(OrderErrorCode.ORDER_ID_NOT_FOUND)
        );
    }

    @Transactional
    public void addOrder(
        Integer memberId,
        AddOrderRequest request
    ) {
        MemberEntity member = memberService.findById(memberId);
        changeMemberPoint(request, member);

        if (orderRepository.existsByOrderId(request.orderId())) {
            throw new OrderException(OrderErrorCode.DUPLICATED_ORDER_ID);
        }

        // 주문 테이블 생성
        OrderEntity order = AddOrderRequest.of(request, member);
        OrderEntity savedOrder = orderRepository.save(order);

        // 주문 디테일 테이블 생성
        for (AddOrderProductInfo info : request.products()) {
            ProductEntity product = productService.findProductById(info.productId());

            OrderDetailEntity entity = new OrderDetailEntity(
                product,
                savedOrder,
                info.price(),
                info.quantity(),
                info.productImgUrl(),
                (int) (info.price() * REWORD_RATE),
                getTodayDate()
            );

            orderDetailRepository.save(entity);
        }
    }

    private void changeMemberPoint(AddOrderRequest request, MemberEntity member) {
        // 포인트 처리
        if (member.getPoint() < request.usedPoint()) {
            throw new OrderException(OrderErrorCode.NOT_ENOUGH_POINTS);
        }

        member.changePoint(request.usedPoint());
    }
}