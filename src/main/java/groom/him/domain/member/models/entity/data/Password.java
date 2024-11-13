package groom.him.domain.member.models.entity.data;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
@Embeddable
public class Password {
    @NotNull
    @Column(length = 64, name = "password")
    private String encryptedPassword;

    @NotNull
    @Column(length = 32, name = "salt")
    private String salt;
}

