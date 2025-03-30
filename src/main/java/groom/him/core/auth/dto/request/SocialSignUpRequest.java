package groom.him.core.auth.dto.request;

import groom.him.core.models.constant.Gender;
import groom.him.domain.member.models.constant.Provider;

public record SocialSignUpRequest(
    String loginId,
    String nickname,
    Gender gender,
    String birth,
    String email,
    Provider provider,
    String socialTokenId,
    CreateAgreementRequest agreement
) {

}
