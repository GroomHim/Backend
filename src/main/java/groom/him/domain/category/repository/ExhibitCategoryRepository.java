package groom.him.domain.category.repository;

import groom.him.domain.category.models.entity.ExhibitCategoryEntity;
import groom.him.domain.qa.models.entity.QaCategoryEntity;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ExhibitCategoryRepository extends JpaRepository<ExhibitCategoryEntity, Integer> {
    @Query(value = """
         WITH RECURSIVE CategoryHierarchy AS (
            SELECT exhibit_category_id, exhibit_category_name, parent_exhibit_category_id, is_leaf
            FROM EXHIBIT_CATEGORY
            WHERE exhibit_category_id in :target
            UNION ALL
            SELECT c.exhibit_category_id, c.exhibit_category_name, c.parent_exhibit_category_id, c.is_leaf
            FROM EXHIBIT_CATEGORY c
            INNER JOIN CategoryHierarchy ch ON c.parent_exhibit_category_id = ch.exhibit_category_id
        )
        SELECT ch.exhibit_category_id
        FROM CategoryHierarchy ch
        WHERE is_leaf = 1;
         """, nativeQuery = true)
    List<Integer> getLeafCategoryIdByTargetCategoryId(@Param("target") List<Integer> target);

    @Query("SELECT c FROM ExhibitCategoryEntity c LEFT JOIN FETCH c.children WHERE c.parentExhibitCategory IS NULL")
    List<ExhibitCategoryEntity> findAllByParentExhibitCategoryIsNull();
}