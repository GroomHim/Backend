package groom.him.core.domain.faq.repository;

import groom.him.core.common.enums.IsPublic;
import groom.him.core.domain.faq.models.entity.FaqEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FaqRepository extends JpaRepository<FaqEntity, Long> {
    List<FaqEntity> findAllByIsPublicOrderByPrioAsc(IsPublic isPublic);
}