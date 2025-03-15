package groom.him.domain.search.service;

import groom.him.domain.member.models.entity.MemberEntity;
import groom.him.domain.search.exception.SearchException;
import groom.him.domain.search.models.enums.SearchErrorCode;
import groom.him.domain.search.repository.SearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchService {
    private final SearchRepository searchRepository;

    public List<String> findSearchList(MemberEntity member){
        ArrayList<String> list = new ArrayList<>();
        searchRepository.findTop5ByMember_MemberIdOrderByRegDtDesc(member.getMemberId()).forEach(entity -> list.add(entity.getSearchWord()));
        return list;
    }

    public void deleteSearch(Long searchId){
        if(searchRepository.findById(searchId).isEmpty()) throw new SearchException(SearchErrorCode.SEARCH_ID_NOT_EXIST);
      searchRepository.deleteById(searchId);
    }
}
