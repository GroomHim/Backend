package groom.him.domain.faq.models.entity;

import groom.him.common.models.entity.AuditingFields;
import groom.him.core.common.converters.IsPublicConverter;
import groom.him.core.common.enums.IsPublic;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Table(name = "FAQ")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class FaqEntity extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // AUTO_INCREMENT 사용
    @Column(name = "faq_id")
    private Integer faqId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "faq_category_id")
    private FaqCategoryEntity faqCategory;

    @NotNull
    @Column(name = "question", length = 50)
    private String question;

    @NotNull
    @Column(name = "answer", length = 255)
    private String answer;

    @NotNull
    @Column(name = "prio", length = 20)
    private String prio;

    @Column(name = "is_public", length = 1)
    @NotNull
    @Convert(converter = IsPublicConverter.class)
    private IsPublic isPublic = IsPublic.CLOSE;
}