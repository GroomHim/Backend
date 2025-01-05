package groom.him.domain.order.repository;

import groom.him.domain.order.models.entity.OrderDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepository extends JpaRepository<OrderDetailEntity, Integer> {
}
