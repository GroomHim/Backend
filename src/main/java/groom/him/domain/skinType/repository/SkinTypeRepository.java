package groom.him.domain.skinType.repository;

import groom.him.domain.skinType.models.entity.SkinTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SkinTypeRepository extends JpaRepository<SkinTypeEntity, Integer> {
    @Query(value = """
        SELECT skin_type_id 
        FROM SKIN_TYPE 
        ORDER BY RAND() 
        LIMIT 1
        """, nativeQuery = true)
    Integer getRandomSkinTypeId();
}