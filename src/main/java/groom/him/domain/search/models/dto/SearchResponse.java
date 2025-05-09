package groom.him.domain.search.models.dto;

import groom.him.domain.search.models.entity.SearchEntity;

public record SearchResponse(
    Integer searchId,
    String searchWord
) {
    public static SearchResponse of(SearchEntity search) {
        return new SearchResponse(search.getSearchId(), search.getSearchWord());
    }
}
