package groom.him.core.auth.dto.request;

import groom.him.core.models.constant.Gender;

public record SignUpRequest(
    String loginId,
    String password,
    String nickname,
    String email,
    Gender gender,
    String birth,
    CreateAgreementRequest agreement
) {

}
