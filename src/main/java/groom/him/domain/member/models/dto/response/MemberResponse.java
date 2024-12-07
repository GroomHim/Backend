package groom.him.domain.member.models.dto.response;

import groom.him.common.models.constant.Gender;
import groom.him.domain.member.models.entity.MemberEntity;

public record MemberResponse(
        String loginId,
        String name,
        String phoneNumber,
        Gender gender,
        String nickname,
        String birth,
        String email
) {
    public static MemberResponse from(MemberEntity member) {
        return new MemberResponse(
                member.getLoginId(),
                member.getName(),
                member.getPhoneNumber(),
                member.getGender(),
                member.getNickname(),
                member.getBirth(),
                member.getEmail()
        );
    }
}
