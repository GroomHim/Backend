package groom.him.domain.search.repository;

import groom.him.domain.search.models.entity.SearchEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SearchRepository extends JpaRepository<SearchEntity,Integer> {
}
