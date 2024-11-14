package groom.him.core.model.product.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
@Entity
@Getter
public class SkinType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer skinTypeId;
    @Column(length = 8)
    private String skinTypeName;
    @Column
    @CreatedDate
    private LocalDateTime regDt;
}
