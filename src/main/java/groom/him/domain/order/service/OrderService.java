package groom.him.domain.order.service;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class OrderService {
    private static final String ORDER_NUMBER_PREFIX = "OD";

    private final ConcurrentHashMap<String, AtomicInteger> orderNumberInDays = new ConcurrentHashMap<>();

    public String getOrderId() {
        String today = getTodayDate();
        AtomicInteger getOrderNumber = orderNumberInDays.computeIfAbsent(today, key -> new AtomicInteger(0));
        String orderNumber = String.format("%06d", getOrderNumber.incrementAndGet());
        return ORDER_NUMBER_PREFIX + today + orderNumber;
    }

    private String getTodayDate() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        return currentDate.format(formatter);
    }
}
