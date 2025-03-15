//package groom.v1.address.models.dto.response;
//
//import groom.v1.address.models.entity.AddressEntity;
//import java.time.LocalDateTime;
//
//public record AddressResponse(
//    Integer addressId,
//    String name,
//    String phoneNumber,
//    String alias,
//    String streetNumber,
//    String address,
//    String addressDetail,
//    String deliveryInfo,
//    Boolean isDefault,
//    LocalDateTime regDt
//) {
//
//    public static AddressResponse of(AddressEntity entity) {
//        return new AddressResponse(entity.getAddressId(), entity.getName(), entity.getPhoneNumber(),
//            entity.getAlias(), entity.getStreetNumber(), entity.getAddress(),
//            entity.getAddressDetail(),
//            entity.getDeliveryInfo(), entity.getIsDefault(), entity.getRegDt());
//    }
//}