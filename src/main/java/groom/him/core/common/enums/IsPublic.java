package groom.him.core.common.enums;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.Arrays;

public enum IsPublic {
    OPEN(true),
    CLOSE(false);

    private final Boolean code;

    IsPublic(Boolean code) {
        this.code = code;
    }

    @JsonValue
    public Boolean getCode() {
        return code;
    }

    @JsonCreator
    public static groom.him.core.common.enums.IsPublic fromCode(Boolean code) {
        return Arrays.stream(groom.him.core.common.enums.IsPublic.values())
            .filter(v -> v.getCode().equals(code))
            .findAny()
            .orElseThrow(() -> new IllegalArgumentException(String.format("공개여부에 %s가 존재하지 않습니다.", code)));
    }
}