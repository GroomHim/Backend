package groom.him.domain.address.models.dto.request;

public record ModifyAddressRequest(
    String name,
    String phoneNumber,
    String alias,
    String streetNumber,
    String address,
    String addressDetail,
    Boolean isDefault
) {
}