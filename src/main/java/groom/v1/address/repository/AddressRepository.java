//package groom.v1.address.repository;
//
//import groom.v1.address.models.entity.AddressEntity;
//import java.util.List;
//import java.util.Optional;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//import org.springframework.stereotype.Repository;
//
//@Repository
//public interface AddressRepository extends JpaRepository<AddressEntity, Integer> {
//    @Query(value = "select a from AddressEntity a where a.member.memberId = :memberId order by a.regDt desc")
//    List<AddressEntity> findAllByMemberId(@Param("memberId") Integer memberId);
//
//    @Query(value = "select a from AddressEntity a where a.member.memberId = :memberId and a.isDefault = true")
//    Optional<AddressEntity> findByMemberIdAndIsDefaultTrue(@Param("memberId") Integer memberId);
//}