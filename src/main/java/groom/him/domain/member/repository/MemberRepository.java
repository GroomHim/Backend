package groom.him.domain.member.repository;

import groom.him.domain.member.models.constant.Provider;
import groom.him.domain.member.models.entity.MemberEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, Integer> {

    Optional<MemberEntity> findByMemberIdAndIsCancelFalse(Integer id);

    Optional<MemberEntity> findByMemberIdAndRefreshToken(Integer id, String refreshToken);

    Optional<MemberEntity> findByLoginIdAndIsCancelFalse(String loginId);

    boolean existsByLoginIdAndSocialTokenIdAndProviderAndIsCancelFalse(
        String loginId, String socialTokenId, Provider provider
    );

    Optional<MemberEntity> findByLoginId(String loginId);

    Optional<MemberEntity> findByNickname(String nickname);
}
