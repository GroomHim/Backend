package groom.him.core.auth.dto.request;


import groom.him.core.auth.models.entity.AgreementEntity;
import groom.him.domain.member.models.entity.MemberEntity;

public record CreateAgreementRequest(
    Boolean termsOfServiceConsent,
    Boolean personalDataConsent,
    Boolean marketingConsent,
    Boolean locationConsent
) {

    public static AgreementEntity from(CreateAgreementRequest request, MemberEntity member) {
        return AgreementEntity.builder()
            .member(member)
            .termsOfServiceConsent(request.termsOfServiceConsent)
            .personalDataConsent(request.personalDataConsent)
            .marketingConsent(request.marketingConsent)
            .locationConsent(request.locationConsent).
            build();
    }
}
