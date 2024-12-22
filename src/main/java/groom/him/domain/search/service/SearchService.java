package groom.him.domain.search.service;

import groom.him.domain.member.models.entity.MemberEntity;
import groom.him.domain.search.models.dto.SearchResponse;
import groom.him.domain.search.models.entity.SearchEntity;
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
}
