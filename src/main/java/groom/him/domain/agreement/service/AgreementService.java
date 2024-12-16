package groom.him.domain.agreement.service;

import groom.him.domain.agreement.models.dto.CreateAgreementRequest;
import groom.him.domain.agreement.models.entity.AgreementEntity;
import groom.him.domain.agreement.repository.AgreementRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AgreementService {
    private final AgreementRepository agreementRepository;

    public AgreementEntity createAgreement(CreateAgreementRequest request){
        AgreementEntity agreement = request.from(request);
        return agreementRepository.save(agreement);
    }
}
