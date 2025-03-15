//package groom.v1.order.models.dto.response;
//
//import groom.v1.order.models.entity.OrderDetailEntity;
//import groom.v1.order.models.enums.OrderStatus;
//import groom.him.domain.product.models.entity.ProductEntity;
//
//public record OrderDetailResponse(
//    Integer productId,
//    String productName,
//    String brandName,
//    String productImgUrl,
//    Integer orderDetailId,
//    Integer price,
//    Integer quantity,
//    OrderStatus orderStatus,
//    String orderDate
//) {
//
//    public static OrderDetailResponse of(OrderDetailEntity entity) {
//        ProductEntity product = entity.getProduct();
//
//        return new OrderDetailResponse(
//            product.getProductId(),
//            product.getProductName(),
//            product.getBrand().getBrandName(),
//            product.getImgUrl(),
//            entity.getOrderDetailId(),
//            entity.getPrice(),
//            entity.getQuantity(),
//            entity.getOrderStatus(),
//            entity.getOrderDate()
//        );
//    }
//}
