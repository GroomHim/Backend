package groom.him.domain.order.service;

import groom.him.domain.member.models.entity.MemberEntity;
import groom.him.domain.member.service.MemberService;
import groom.him.domain.order.exception.OrderErrorCode;
import groom.him.domain.order.exception.OrderException;
import groom.him.domain.order.models.dto.request.AddOrderRequest;
import groom.him.domain.order.models.dto.request.OrderProductInfo;
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

    @Transactional
    public void addOrder(
        Integer memberId,
        AddOrderRequest request
    ) {
        MemberEntity member = memberService.findById(memberId);
        changeMemberPoint(request, member);

        // 주문 테이블 생성
        OrderEntity order = AddOrderRequest.of(request, member);
        OrderEntity savedOrder = orderRepository.save(order);

        // 주문 디테일 테이블 생성
        for (OrderProductInfo info : request.products()) {
            ProductEntity product = productService.findProductById(info.productId());

            OrderDetailEntity entity = new OrderDetailEntity(
                product,
                savedOrder,
                info.price(),
                info.quantity(),
                (int) (info.price() * REWORD_RATE)
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
