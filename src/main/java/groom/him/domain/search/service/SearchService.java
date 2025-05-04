package groom.him.domain.search.service;

import groom.him.domain.member.models.entity.MemberEntity;
import groom.him.domain.member.service.MemberService;
import groom.him.domain.search.exception.SearchException;
import groom.him.domain.search.models.entity.SearchEntity;
import groom.him.domain.search.models.enums.SearchErrorCode;
import groom.him.domain.search.repository.SearchRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SearchService {
    private final SearchRepository searchRepository;
    private final MemberService memberService;

    public void saveSearch(Integer memberId, String word) {
        MemberEntity member = memberService.findById(memberId);
        SearchEntity entity = new SearchEntity(member, word);
        searchRepository.save(entity);
    }

    public List<String> findMemberSearchWordTop5(Integer memberId) {
        return searchRepository.findTop5ByMember_MemberIdOrderByRegDtDesc(memberId)
            .stream().map(SearchEntity::getSearchWord)
            .toList();
    }

    public void deleteSearch(Long searchId) {
        if (searchRepository.findById(searchId).isEmpty()) {
            throw new SearchException(SearchErrorCode.SEARCH_ID_NOT_EXIST);
        }
        searchRepository.deleteById(searchId);
    }
}
