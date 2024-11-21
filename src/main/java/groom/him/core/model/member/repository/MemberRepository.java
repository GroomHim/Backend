package groom.him.core.model.member.repository;

import groom.him.domain.member.models.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, Integer> {
    Optional<MemberEntity> findByMemberIdAndIsCancelFalse(Integer id);

    Optional<MemberEntity> findByMemberIdAndRefreshToken(Integer id, String refreshToken);

    Optional<MemberEntity> findByLoginIdAndIsCancelFalse(String loginId);

    Optional<MemberEntity> findByCiAndIsCancelFalse(String ci);

    Optional<MemberEntity> findByLoginId(String loginId);

    Optional<MemberEntity> findByNickname(String nickname);
}
