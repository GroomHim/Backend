package groom.him.domain.agreement.models.dto;

import groom.him.domain.agreement.models.entity.AgreementEntity;
import groom.him.domain.member.models.entity.MemberEntity;
import jakarta.persistence.Column;

public record CreateAgreementRequest (
    Integer memberId,
    Boolean termsOfServiceConsent,
    Boolean personalDataConsent,
    Boolean marketingConsent,
    Boolean locationConsent
){
  public static AgreementEntity from(CreateAgreementRequest request){
    return AgreementEntity.builder()
        .termsOfServiceConsent(request.termsOfServiceConsent)
        .personalDataConsent(request.personalDataConsent)
        .marketingConsent(request.marketingConsent)
        .locationConsent(request.locationConsent).
        build();
  }
}
