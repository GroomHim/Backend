package groom.him.domain.member.models.entity.data;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Password {
    @NotNull
    @Column(length = 64, name = "password")
    private String encryptedPassword;

    @NotNull
    @Column(length = 32, name = "salt")
    private String salt;
    @Builder
    public Password(String encryptedPassword, String salt){
        this.encryptedPassword = encryptedPassword;
        this.salt = salt;
    }
}

