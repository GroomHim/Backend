package groom.him.domain.notice.models.entity;

import groom.him.core.models.entity.AuditingFields;
import groom.him.core.common.converters.IsPublicConverter;
import groom.him.core.common.enums.IsPublic;
import groom.him.domain.member.models.entity.MemberEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
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
@Table(name = "NOTICE")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NoticeEntity extends AuditingFields {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_id")
    private Integer noticeId;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private MemberEntity member;

    @NotNull
    @Column(name = "title", length = 20)
    private String title;

    @NotNull
    @Column(name = "content", length = 255)
    private String content;

    @NotNull
    @Column(name = "is_public", length = 1)
    @Convert(converter = IsPublicConverter.class)
    private IsPublic isPublic = IsPublic.CLOSE;
}