//package groom.v1.cart.repository;
//
//import groom.v1.cart.models.entity.CartEntity;
//import groom.him.domain.member.models.entity.MemberEntity;
//import groom.him.domain.product.models.entity.ProductEntity;
//import java.util.List;
//import java.util.Optional;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//
//@Repository
//public interface CartRepository extends JpaRepository<CartEntity, Integer> {
//    Optional<CartEntity> findByMemberAndProduct(MemberEntity member, ProductEntity product);
//
//    List<CartEntity> findAllByMemberOrderByRegDt(MemberEntity member);
//
//    Integer countDistinctByMember_MemberId(Integer memberId);
//}