package groom.him.core.domain.faq.models.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import groom.him.core.common.converters.IsPublicConverter;
import groom.him.core.common.enums.IsPublic;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Getter
@Table(name = "FAQ")
@Entity
public class FaqEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // AUTO_INCREMENT 사용
    @Column(name = "faq_id")
    private Long faqId;

    @Column(name = "faq_category_id")
    private Long faqCategoryId;

    @Column(name = "question", length = 50)
    private String question;

    @Column(name = "answer", length = 255)
    private String answer;

    @Column(name = "prio", length = 20)
    private String prio;

    @Column(name = "is_public", length = 1)
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