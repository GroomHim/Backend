package groom.him.domain.member.models.dto.request;

public record ModifyMyInfoRequest(
        String nickname,
        String email
) {
}