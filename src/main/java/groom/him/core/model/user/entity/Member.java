package groom.him.core.model.user.entity;

import groom.him.core.model.product.entity.SkinType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
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
public class Member implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer memberId;
    @ManyToOne()
    private SkinType skinTypeId;
    @Column(length = 15)
    private String loginId;
    @Column(length = 64)
    private String password;
    @Column(length = 30)
    private String name;
    @Column(length = 11)
    private String phoneNumber;
    @Enumerated @Column(length = 1)
    private Gender gender;
    @Column(length = 20)
    private String nickname;
    @Column(length = 8)
    private String birth;
    @Column
    private String ci;
    @Column(length = 10)
    private String provider;
    @Column
    @CreatedDate
    private LocalDateTime regDt;
    @Column
    @LastModifiedDate
    private LocalDateTime udtDt;
    @Column
    private Boolean isCancel;
    @Enumerated @Column
    private Role role;
    @Column(length = 32)
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

    enum Gender {
         M, W
    }
}
