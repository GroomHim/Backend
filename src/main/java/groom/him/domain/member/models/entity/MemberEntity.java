package groom.him.domain.member.models.entity;

import groom.him.common.models.constant.Gender;
import groom.him.common.models.constant.Role;
import groom.him.common.models.constant.SkinType;
import groom.him.common.models.entity.AuditingFields;
import groom.him.domain.member.models.constant.Provider;
import groom.him.domain.member.models.entity.data.Password;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "MEMBER")
@Entity
public class MemberEntity extends AuditingFields {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long memberId;

    @Enumerated(EnumType.STRING)
    private SkinType skinType;

    @NotNull
    @Column(length = 15, name = "login_id")
    private String loginId;

    @Embedded
    private Password password;

    @NotNull
    @Column(length = 30, name = "name")
    private String name;

    @NotNull
    @Column(length = 11, name = "phone_number")
    private String phoneNumber;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(length = 1, name = "gender")
    private Gender gender;

    @NotNull
    @Column(length = 20, name = "nickname")
    private String nickname;

    @NotNull
    @Column(length = 8, name = "birth")
    private String birth;

    @NotNull
    @Column(name = "ci")
    private String ci;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(length = 10, name = "provider")
    private Provider provider;

    @Column(name = "social_token_id")
    private String socialTokenId;

    @NotNull
    @Column(length = 200, name = "refresh_token")
    private String refreshToken;

    @Column(name = "is_cancel")
    private Boolean isCancel;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;
}