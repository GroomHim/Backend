package groom.him.domain.member.models.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "BANWORD")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class BanwordEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "banword_id")
    Integer banwordId;

    @Column(name = "banword")
    String banword;

    @Column(name = "reg_dt")
    @CreatedDate
    LocalDateTime regDt;

    @Column(name = "is_valid")
    Boolean isValid;
}
