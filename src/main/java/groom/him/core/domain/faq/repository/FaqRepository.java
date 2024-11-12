package groom.him.core.domain.faq.repository;

import groom.him.core.domain.faq.models.entity.FaqEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FaqRepository extends JpaRepository<FaqEntity, Long>, FaqRepositoryCustom {
}