package groom.him.domain.qa.models.dto.request;

public record QaRequest(
    Integer categoryId,
    String title,
    String content
) {
}