package groom.him.domain.member.models.entity;

import groom.him.core.models.constant.Gender;
import groom.him.core.models.constant.Role;
import groom.him.core.models.entity.AuditingFields;
import groom.him.domain.skinType.models.entity.SkinTypeEntity;
import groom.him.domain.member.models.constant.Provider;
import groom.him.domain.member.models.entity.data.Password;
import jakarta.persistence.*;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "skin_type_id")
    private SkinTypeEntity skinTypeEntity;

    @NotNull
    @Column(length = 15, name = "login_id")
    private String loginId;

    @Embedded
    @Column(length = 64, name = "password")
    private Password password;

    @NotNull
    @Column(length = 30, name = "name")
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(length = 1, name = "gender")
    private Gender gender;

    @NotNull
    @Column(length = 20, name = "nickname")
    private String nickname;

    @NotNull
    @Column(length = 10, name = "birth")
    private String birth;

    @NotNull
    @Column(length = 50, name = "email")
    private String email;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(length = 10, name = "provider")
    private Provider provider;

    @Column(name = "social_token_id")
    private String socialTokenId;

    @Column(length = 200, name = "refresh_token")
    private String refreshToken;

    @Column(name = "is_cancel")
    private Boolean isCancel;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;


    public String getSalt() {
        return this.password.getSalt();
    }

    @Builder
    public MemberEntity(
        Integer memberId,
        SkinTypeEntity skinType,
        String loginId,
        Password password,
        String name,
        Gender gender,
        String nickname,
        String birth,
        String email,
        Provider provider,
        String socialTokenId,
        String refreshToken,
        Boolean isCancel,
        Role role
    ) {
        this.memberId = memberId;
        this.skinTypeEntity = skinType;
        this.loginId = loginId;
        this.password = password;
        this.name = name;
        this.gender = gender;
        this.nickname = nickname;
        this.birth = birth;
        this.email = email;
        this.provider = provider;
        this.socialTokenId = socialTokenId;
        this.refreshToken = refreshToken;
        this.isCancel = isCancel;
        this.role = role;
    }

    public void changeRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void changePassword(Password password) {
        this.password = password;
    }

    public void changeNicknameAndEmail(String nickname, String email) {
        this.nickname = nickname;
        this.email = email;
    }

    public void changeSkinType(SkinTypeEntity skinTypeEntity) {
        this.skinTypeEntity = skinTypeEntity;
    }

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
}