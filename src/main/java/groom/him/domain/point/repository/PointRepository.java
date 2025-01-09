package groom.him.domain.point.repository;

import groom.him.domain.point.models.entity.PointHistoryEntity;
import groom.him.domain.point.models.enums.PointHistoryType;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PointRepository extends JpaRepository<PointHistoryEntity,Integer> {
  Optional<List<PointHistoryEntity>> findTop15ByMemberMemberIdOrderByRegDtDesc(Integer memberId);

  Optional<List<PointHistoryEntity>> findImmutableByMemberMemberIdAndPointHistoryTypeEquals(
      Integer memberId, PointHistoryType type);
}
