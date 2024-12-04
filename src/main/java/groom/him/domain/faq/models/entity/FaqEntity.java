package groom.him.domain.faq.models.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import groom.him.core.common.converters.IsPublicConverter;
import groom.him.core.common.enums.IsPublic;
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
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Getter
@Table(name = "FAQ")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class FaqEntity {

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

    @Column(updatable = false, name = "reg_dt")
    @CreationTimestamp
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime regDt;

    @Column(name = "udt_dt")
    @UpdateTimestamp
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime updDt;
}