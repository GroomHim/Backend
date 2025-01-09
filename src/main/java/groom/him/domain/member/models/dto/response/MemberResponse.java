package groom.him.domain.member.models.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import groom.him.common.models.constant.Gender;
import groom.him.domain.member.models.entity.MemberEntity;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;

@JsonInclude(NON_EMPTY)
public record MemberResponse(
    String loginId,
    String name,
    Integer skinTypeId,
    String skinTypeName,
    String phoneNumber,
    Gender gender,
    String nickname,
    String birth,
    String email
) {
    public static MemberResponse from(MemberEntity member) {
        if (member.getSkinTypeEntity() != null) {
            return new MemberResponse(
                member.getLoginId(),
                member.getName(),
                member.getSkinTypeEntity().getSkinTypeId(),
                member.getSkinTypeEntity().getSkinTypeName(),
                member.getPhoneNumber(),
                member.getGender(),
                member.getNickname(),
                member.getBirth(),
                member.getEmail()
            );
        }

        return new MemberResponse(
            member.getLoginId(),
            member.getName(),
            null,
            null,
            member.getPhoneNumber(),
            member.getGender(),
            member.getNickname(),
            member.getBirth(),
            member.getEmail()
        );
    }
}