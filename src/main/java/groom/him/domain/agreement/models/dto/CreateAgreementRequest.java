package groom.him.domain.agreement.models.dto;

import groom.him.domain.agreement.models.entity.AgreementEntity;
import jakarta.persistence.Column;

public record CreateAgreementRequest (
    Integer memberId,
    Boolean termsOfServiceConsent,
    Boolean personalDataConsent,
    Boolean marketingConsent,
    Boolean locationConsent
){
  public static AgreementEntity from(CreateAgreementRequest request){
    AgreementEntity agreement = new AgreementEntity();
    
  }
}
