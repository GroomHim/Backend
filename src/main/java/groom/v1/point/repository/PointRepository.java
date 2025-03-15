//package groom.v1.point.repository;
//
//import groom.v1.point.models.entity.PointHistoryEntity;
//import groom.v1.point.models.enums.PointHistoryType;
//import java.util.List;
//import java.util.Optional;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//
//@Repository
//public interface PointRepository extends JpaRepository<PointHistoryEntity,Integer> {
//  Optional<List<PointHistoryEntity>> findTop15ByMemberMemberIdOrderByRegDtDesc(Integer memberId);
//
//  Optional<List<PointHistoryEntity>> findImmutableByMemberMemberIdAndPointHistoryTypeEquals(
//      Integer memberId, PointHistoryType type);
//}
