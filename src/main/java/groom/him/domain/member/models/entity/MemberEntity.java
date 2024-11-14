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
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "MEMBER")
@Entity
public class MemberEntity extends AuditingFields implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Integer memberId;

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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.stream(role.toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return this.password.getEncryptedPassword();
    }

    @Override
    public String getUsername() {
        return name;
    }

    @Builder
    public MemberEntity(
            Integer memberId,
            SkinType skinType,
            String loginId,
            Password password,
            String name,
            String phoneNumber,
            Gender gender,
            String nickname,
            String birth,
            String ci,
            Provider provider,
            String socialTokenId,
            String refreshToken,
            Boolean isCancel,
            Role role
    ){
        this.memberId = memberId;
        this.skinType = skinType;
        this.loginId = loginId;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.nickname = nickname;
        this.birth = birth;
        this.ci = ci;
        this.provider = provider;
        this.socialTokenId = socialTokenId;
        this.refreshToken = refreshToken;
        this.isCancel = isCancel;
        this.role = role;
    }

    public void changeRefreshToken(String refreshToken){
        this.refreshToken = refreshToken;
    }
}