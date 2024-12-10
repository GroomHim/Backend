package groom.him.domain.search.models.dto;

import groom.him.domain.product.models.dto.response.ProductBriefResponse;
import groom.him.domain.product.models.entity.ProductEntity;
import groom.him.domain.search.models.entity.SearchEntity;
import lombok.Builder;

import java.time.LocalDateTime;
@Builder
public record SearchResponse (
    Integer memberId,

    String searchWord,

    LocalDateTime regDt

){
    public static SearchResponse of(SearchEntity search, Integer memberId) {
        return new SearchResponse(memberId, search.getSearchWord(), search.getRegDt());
    }
}
