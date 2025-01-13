package groom.him.domain.address.models.dto.request;

public record AddAddressRequest(
    String name,
    String phoneNumber,
    String alias,
    String streetNumber,
    String address,
    String addressDetail,
    Boolean isDefault
) {
}