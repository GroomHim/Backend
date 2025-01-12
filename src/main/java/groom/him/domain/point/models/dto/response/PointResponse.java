package groom.him.domain.point.models.dto.response;

public record PointResponse (
    Integer point,
    Integer pendingPoint,
    Integer expiringPoint
){ }
