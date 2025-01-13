package groom.him.domain.address.models.entity;

import groom.him.common.models.entity.AuditingFields;
import groom.him.domain.address.models.dto.request.ModifyAddressRequest;
import groom.him.domain.member.models.entity.MemberEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "ADDRESS")
@Entity
public class AddressEntity extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Integer addressId;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private MemberEntity member;

    @NotNull
    @Column(length = 30, name = "name")
    private String name;

    @NotNull
    @Column(length = 20, name = "phone_number")
    private String phoneNumber;

    @NotNull
    @Column(length = 30, name = "alias")
    private String alias;

    @NotNull
    @Column(name = "street_number")
    private String streetNumber;

    @NotNull
    @Column(length = 200, name = "address")
    private String address;

    @NotNull
    @Column(length = 30, name = "address_detail")
    private String addressDetail;

    @NotNull
    @Column(name = "is_default")
    private Boolean isDefault;

    @Builder
    public AddressEntity(MemberEntity member, String name, String phoneNumber, String alias,
        String streetNumber, String address, String addressDetail, Boolean isDefault) {
        this.member = member;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.alias = alias;
        this.streetNumber = streetNumber;
        this.address = address;
        this.addressDetail = addressDetail;
        this.isDefault = isDefault;
    }

    public void changeIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }

    public void modifyAddress(ModifyAddressRequest request) {
        this.name = request.name();
        this.phoneNumber = request.phoneNumber();
        this.alias = request.alias();
        this.streetNumber = request.streetNumber();
        this.address = request.address();
        this.addressDetail = request.addressDetail();
        this.isDefault = request.isDefault();
    }
}