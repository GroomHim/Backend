package groom.him.core.domain.notice.models.entities;

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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Getter
@Table(name = "NOTICE")
@Entity
public class NoticeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // AUTO_INCREMENT 사용
    @Column(name = "notice_id")
    private Long noticeId;

//    TODO: MemberEntity 코드 머지 후 수정 예정
//    @ManyToOne
//    @JoinColumn(name = "member_id")
//    private MemberEntity member;
    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "title", length = 20)
    private String title;

    @Column(name = "content", length = 255)
    private String content;

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