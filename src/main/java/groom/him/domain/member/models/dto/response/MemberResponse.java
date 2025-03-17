package groom.him.domain.member.models.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import groom.him.core.models.constant.Gender;
import groom.him.domain.member.models.entity.MemberEntity;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;

@JsonInclude(NON_EMPTY)
public record MemberResponse(
    String loginId,
    String name,
    Integer skinTypeId,
    String skinTypeName,
    Gender gender,
    String nickname,
    String birth,
    String email
) {
    public static MemberResponse of(MemberEntity member) {
        return new MemberResponse(
            member.getLoginId(),
            member.getName(),
            member.getSkinTypeEntity() != null ? member.getSkinTypeEntity().getSkinTypeId() : null,
            member.getSkinTypeEntity() != null ? member.getSkinTypeEntity().getSkinTypeName() : null,
            member.getGender(),
            member.getNickname(),
            member.getBirth(),
            member.getEmail()
        );
    }
}