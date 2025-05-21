package groom.him.domain.search.service;

import groom.him.domain.member.models.entity.MemberEntity;
import groom.him.domain.member.service.MemberService;
import groom.him.domain.search.exception.SearchException;
import groom.him.domain.search.models.dto.SearchResponse;
import groom.him.domain.search.models.entity.SearchEntity;
import groom.him.domain.search.models.enums.SearchErrorCode;
import groom.him.domain.search.repository.SearchRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SearchService {
    private final SearchRepository searchRepository;
    private final MemberService memberService;

    @Transactional
    public void saveSearch(Integer memberId, String word) {
        searchRepository.findByMember_MemberIdAndSearchWord(memberId, word)
            .ifPresentOrElse(entity -> {
                    entity.updateSearchAt();
                    searchRepository.save(entity);
                },
                () -> {
                    MemberEntity member = memberService.findByMemberIdAndIsCancelFalse(memberId);
                    SearchEntity entity = new SearchEntity(member, word);
                    searchRepository.save(entity);
                });
    }

    public List<SearchResponse> findMemberSearchWordTop5(Integer memberId) {
        return searchRepository.findTop5ByMember_MemberIdOrderBySearchedAtDesc(memberId)
            .stream().map(SearchResponse::of)
            .toList();
    }

    @Transactional
    public void deleteSearch(Long searchId) {
        if (searchRepository.findById(searchId).isEmpty()) {
            throw new SearchException(SearchErrorCode.SEARCH_ID_NOT_EXIST);
        }
        searchRepository.deleteById(searchId);
    }

    @Transactional
    public void deleteAll(Integer memberId) {
        searchRepository.deleteByMember_MemberId(memberId);
    }
}
