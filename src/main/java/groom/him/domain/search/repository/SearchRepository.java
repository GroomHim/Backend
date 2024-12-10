package groom.him.domain.search.repository;

import groom.him.domain.member.models.entity.MemberEntity;
import groom.him.domain.search.models.entity.SearchEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SearchRepository extends JpaRepository<SearchEntity, Long> {
    Integer countByMember(MemberEntity member);
    List<SearchEntity> findTop5ByMemberOrderByRegDtDesc(MemberEntity member);
}
