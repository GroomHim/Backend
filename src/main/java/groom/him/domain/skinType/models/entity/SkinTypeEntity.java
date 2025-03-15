package groom.him.domain.skinType.models.entity;

import groom.him.core.models.entity.RegisterDateFields;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "SKIN_TYPE")
@Entity
public class SkinTypeEntity extends RegisterDateFields {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "skin_type_id")
    private Integer skinTypeId;

    @NotNull
    @Column(name = "skin_type_name", length = 10)
    private String skinTypeName;

    @NotNull
    @Column(name = "rate")
    private BigDecimal rate;

    @NotNull
    @Column(name = "description")
    private String description;
}