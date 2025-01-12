package groom.him.domain.point.models.entity;

import groom.him.common.models.entity.AuditingFields;
import groom.him.domain.member.models.entity.MemberEntity;
import groom.him.domain.order.models.entity.OrderEntity;
import groom.him.domain.point.models.enums.PointHistoryType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@Table(name = "POINT_HISTORY")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class PointHistoryEntity extends AuditingFields {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "point_history_id")
  private Integer pointHistoryId;

  @Column(name = "point")
  private Integer point;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id")
  private MemberEntity member;

  @Column(name = "point_history_type")
  @Enumerated(EnumType.STRING)
  private PointHistoryType pointHistoryType;

  @Column(name = "reg_dt")
  private LocalDateTime regDt;

  @Column(name = "valid_from_dt")
  private LocalDateTime validFromDt;

  @Column(name = "valid_to_dt")
  private LocalDateTime validToDt;

  @Column(name = "is_applied")
  private Boolean isApplied;

  @Column(name = "product_name")
  private String productName;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "order_id")
  private OrderEntity order;

  public void changePoint(Integer point){
    this.point = point;
  }

  public void changeApplied(Boolean isApplied){
    this.isApplied = isApplied;
  }

  public void changeValidFromDt(LocalDateTime validFrom){
    this.validFromDt = validFrom;
  }

  public void changeValidToDt(LocalDateTime validTo){
    this.validToDt = validTo;
  }
}
