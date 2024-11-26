package groom.him.core.auth.dto.request;

import groom.him.common.models.constant.Gender;

public record SignUpRequest(
        String ci,
        String name,
        Gender gender,
        String password,
        String phoneNumber,
        String birth,
        String loginId,
        String email,
        String nickname
) {
}
