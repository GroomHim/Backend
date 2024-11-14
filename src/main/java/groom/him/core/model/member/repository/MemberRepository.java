package groom.him.core.model.member.repository;

import groom.him.domain.member.models.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity,Integer> {
    Optional<MemberEntity> findByIdAndIsEnabledTrue(Integer id);
    Optional<MemberEntity> findByIdAndRefreshToken(Integer id, String refreshToken);

    Optional<MemberEntity> findByLoginIdAndIsEnabledTrue(String loginId);
}
