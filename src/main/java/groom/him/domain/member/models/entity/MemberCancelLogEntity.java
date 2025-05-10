package groom.him.domain.member.models.entity;

import groom.him.core.models.entity.RegisterDateFields;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "MEMBER_CANCEL_LOG")
@Entity
public class MemberCancelLogEntity extends RegisterDateFields {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_cancel_log_id")
    private Integer memberCancelLogId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private MemberEntity member;

    @NotNull
    @Column(length = 50, name = "reason")
    private String reason;

    protected MemberCancelLogEntity(MemberEntity member, String reason) {
        this.member = member;
        this.reason = reason;
    }

    public static MemberCancelLogEntity of(MemberEntity member, String reason) {
        return new MemberCancelLogEntity(member, reason);
    }
}