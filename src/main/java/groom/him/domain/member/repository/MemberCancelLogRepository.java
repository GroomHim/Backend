package groom.him.domain.member.repository;

import groom.him.domain.member.models.entity.MemberCancelLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberCancelLogRepository extends JpaRepository<MemberCancelLogEntity, Integer> {
}