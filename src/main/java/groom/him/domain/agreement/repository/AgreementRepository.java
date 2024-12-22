package groom.him.domain.agreement.repository;

import groom.him.domain.agreement.models.entity.AgreementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgreementRepository extends JpaRepository<AgreementEntity,Integer> {

}
