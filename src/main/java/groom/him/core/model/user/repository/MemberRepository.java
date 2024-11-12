package groom.him.core.model.user.repository;

import com.querydsl.jpa.impl.JPAQuery;
import groom.him.core.model.user.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member,Integer> {
    Optional<Member> findByIdAndIsEnabledTrue(Integer id);
    Optional<Member> findByIdAndRefreshToken(Integer id, String refreshToken);
}
