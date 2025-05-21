package groom.him.domain.search.repository;

import groom.him.domain.search.models.entity.SearchEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SearchRepository extends JpaRepository<SearchEntity, Long> {

    List<SearchEntity> findTop5ByMember_MemberIdOrderBySearchedAtDesc(Integer memberId);

    Optional<SearchEntity> findByMember_MemberIdAndSearchWord(Integer memberId,
        String searchWord);

    void deleteByMember_MemberId(Integer memberId);
}
