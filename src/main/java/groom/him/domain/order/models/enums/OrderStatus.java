package groom.him.domain.order.models.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum OrderStatus {
    ORDER_COMPLETED("주문완료"),
    PURCHASE_CONFIRMED("구매확정"),
    ;

    private final String code;

    OrderStatus(String code) {
        this.code = code;
    }

    @JsonCreator
    public static OrderStatus fromCode(String code) {
        return Arrays.stream(OrderStatus.values())
            .filter(v -> v.getCode().equals(code))
            .findAny()
            .orElseThrow(
                () -> new IllegalArgumentException(String.format("주문 상태에 %s가 존재하지 않습니다.", code)));
    }

    @JsonValue
    public String getCode() {
        return code;
    }
}