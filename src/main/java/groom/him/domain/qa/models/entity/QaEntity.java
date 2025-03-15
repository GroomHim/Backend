package groom.him.domain.qa.models.entity;

import groom.him.core.models.entity.AuditingFields;
import groom.him.domain.member.models.entity.MemberEntity;
import groom.him.domain.qa.models.converters.QaStatusConverter;
import groom.him.domain.qa.models.enums.QaStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
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
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "QA")
@Entity
public class QaEntity extends AuditingFields {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "qa_id")
    private Integer qaId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "qa_category_id")
    private QaCategoryEntity qaCategory;

    @NotNull
    @Column(name = "title", length = 50)
    private String title;

    @NotNull
    @Column(name = "content", length = 255)
    private String content;

    @NotNull
    @Convert(converter = QaStatusConverter.class)
    @Column(name = "status", length = 10)
    private QaStatus status = QaStatus.PENDING;

    @Builder
    public QaEntity(MemberEntity member, QaCategoryEntity qaCategory, String title,
        String content) {
        this.member = member;
        this.qaCategory = qaCategory;
        this.title = title;
        this.content = content;
    }
}