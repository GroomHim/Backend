package groom.him.core.auth.dto.request;

import groom.him.domain.member.models.constant.Provider;

public record SocialSignInRequest(
    String loginId,
    String socialTokenId,
    Provider provider
) {

}
