package groom.him.domain.qa.models.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.Arrays;

public enum QaStatus {
    PENDING("답변 대기"),
    PROGRESS("답변 준비중"),
    COMPLETE("답변 완료");

    private final String code;

    QaStatus(String code) {
        this.code = code;
    }

    @JsonValue
    public String getCode() {
        return code;
    }

    @JsonCreator
    public static groom.him.domain.qa.models.enums.QaStatus fromCode(String code) {
        return Arrays.stream(groom.him.domain.qa.models.enums.QaStatus.values())
            .filter(v -> v.getCode().equals(code))
            .findAny()
            .orElseThrow(
                () -> new IllegalArgumentException(String.format("문의내역 상태에 %s가 존재하지 않습니다.", code)));
    }
}