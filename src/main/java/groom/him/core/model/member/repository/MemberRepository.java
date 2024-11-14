package groom.him.core.model.member.repository;

import groom.him.domain.member.models.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, Integer> {
    Optional<MemberEntity> findByMemberIdAndIsCancelTrue(Integer id);

    Optional<MemberEntity> findByMemberIdAndRefreshToken(Integer id, String refreshToken);

    Optional<MemberEntity> findByLoginIdAndIsCancelTrue(String loginId);
}
