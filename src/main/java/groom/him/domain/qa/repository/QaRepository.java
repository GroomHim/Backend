package groom.him.domain.qa.repository;

import groom.him.domain.qa.models.entity.QaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QaRepository extends JpaRepository<QaEntity, Long>, QaRepositoryCustom {
}