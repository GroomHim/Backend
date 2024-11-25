package groom.him.domain.qa.repository;

import groom.him.domain.qa.models.entity.QaCategoryEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QaCategoryRepository extends JpaRepository<QaCategoryEntity, Long> {
    @EntityGraph(attributePaths = {"children"})
    List<QaCategoryEntity> findAllByParentQaCategoryIsNull();

    Optional<QaCategoryEntity> findByQaCategoryIdAndIsLeafTrue(Integer qaCategoryId);
}