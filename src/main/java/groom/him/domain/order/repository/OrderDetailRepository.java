package groom.him.domain.order.repository;

import groom.him.domain.order.models.entity.OrderDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetailEntity, Integer> {
    List<OrderDetailEntity> findByOrder_OrderId(String orderId);
}
