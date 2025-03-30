package groom.him.core.auth.models.entity;

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
    private Integer agreementId;

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity member;

    @Column(name = "terms_of_service_consent")
    private Boolean termsOfServiceConsent;

    @Column(name = "personal_data_consent")
    private Boolean personalDataConsent;

    @Column(name = "marketing_consent")
    private Boolean marketingConsent;

    @Column(name = "location_consent")
    private Boolean locationConsent;

    @Builder
    public AgreementEntity(
        MemberEntity member,
        Boolean termsOfServiceConsent,
        Boolean personalDataConsent,
        Boolean marketingConsent,
        Boolean locationConsent
    ) {
        this.member = member;
        this.termsOfServiceConsent = termsOfServiceConsent;
        this.personalDataConsent = personalDataConsent;
        this.marketingConsent = marketingConsent;
        this.locationConsent = locationConsent;
    }

    public void modifyMember(MemberEntity member) {
        this.member = member;
    }
}
