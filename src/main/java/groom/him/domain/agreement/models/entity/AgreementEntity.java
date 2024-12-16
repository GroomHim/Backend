package groom.him.domain.agreement.models.entity;

import groom.him.domain.agreement.models.dto.CreateAgreementRequest;
import groom.him.domain.member.models.entity.MemberEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Table(name = "AGREEMENT")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class AgreementEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer agreementId;
    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    MemberEntity member;
    @Column(name = "terms_of_service_consent")
    Boolean termsOfServiceConsent;
    @Column(name = "personal_data_consent")
    Boolean personalDataConsent;
    @Column(name = "marketing_consent")
    Boolean marketingConsent;
    @Column(name = "location_consent")
    Boolean locationConsent;
}
