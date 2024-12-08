package groom.him.domain.address.models.dto.response;

import groom.him.domain.address.models.entity.AddressEntity;
import java.time.LocalDateTime;

public record AddressResponse(
    String name,
    String phoneNumber,
    String alias,
    String address,
    String addressDetail,
    Boolean isDefault,
    LocalDateTime regDt
) {
    public static AddressResponse of(AddressEntity entity) {
        return new AddressResponse(entity.getName(), entity.getPhoneNumber(), entity.getAlias(),
            entity.getAddress(), entity.getAddressDetail(), entity.getIsDefault(),
            entity.getRegDt());
    }
}