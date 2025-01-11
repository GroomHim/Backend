package groom.him;

import groom.him.domain.order.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
@ActiveProfiles("test")
public class TestBase {

    @Autowired
    private OrderService orderService;

    @Test
    public void testWithMultipleThreads() throws InterruptedException {
        int threadCount = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(25);
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    String orderId = orderService.getOrderId();
                    System.out.println(orderId);
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
    }
}