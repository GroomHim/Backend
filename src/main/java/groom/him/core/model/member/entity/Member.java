package groom.him.core.model.member.entity;

import groom.him.core.model.product.entity.SkinType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@Entity
@Getter
@Builder
@RequiredArgsConstructor
public class Member implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer memberId;

    @ManyToOne(fetch = FetchType.LAZY)
    @Column(name = "skintype_id")
    private SkinType skinTypeId;

    @Column(length = 15, name = "login_id")
    private String loginId;

    @Column(length = 64, name = "password")
    private String password;

    @Column(length = 30, name = "name")
    private String name;

    @Column(length = 11, name = "phone_number")
    private String phoneNumber;

    @Enumerated @Column(length = 1, name = "gender")
    private Gender gender;

    @Column(length = 20, name = "nickname")
    private String nickname;

    @Column(length = 8, name = "birth")
    private String birth;

    @Column(name = "ci")
    private String ci;

    @Column(length = 10, name = "provider")
    private String provider;

    @Column(length = 200, name = "refresh_token")
    private String refreshToken;

    @Column(name = "reg_dt")
    @CreatedDate
    private LocalDateTime regDt;

    @Column(name = "udt_dt")
    @LastModifiedDate
    private LocalDateTime udtDt;

    @Column(name = "is_cancel")
    private Boolean isCancel;

    @Enumerated @Column(name = "role")
    private Role role;

    @Column(length = 32, name = "salt")
    private String salt;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.stream(role.toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return this.name;
    }

    public enum Role{
        USER, ADMIN
    }

    public enum Gender {
         M, W
    }
}
