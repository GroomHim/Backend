package groom.him.core.auth.repository;

import groom.him.core.auth.models.entity.AgreementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgreementRepository extends JpaRepository<AgreementEntity, Integer> {

}
