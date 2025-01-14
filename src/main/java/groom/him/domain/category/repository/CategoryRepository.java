package groom.him.domain.category.repository;

import groom.him.domain.category.models.entity.CategoryEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Integer> {
    Optional<CategoryEntity> findByCategoryName(String categoryName);
}