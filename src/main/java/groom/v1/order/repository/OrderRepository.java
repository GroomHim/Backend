//package groom.v1.order.repository;
//
//import groom.v1.order.models.entity.OrderEntity;
//import org.springframework.data.jpa.repository.JpaRepository;
//
//import java.util.List;
//
//public interface OrderRepository extends JpaRepository<OrderEntity, String> {
//    boolean existsByOrderId(String orderId);
//
//    List<OrderEntity> findByMember_memberIdOrderByOrderIdDesc(Integer memberId);
//}
