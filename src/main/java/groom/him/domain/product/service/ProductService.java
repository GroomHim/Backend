package groom.him.domain.product.service;

import groom.him.domain.member.models.entity.MemberEntity;
import groom.him.domain.product.models.dto.response.ProductBriefResponse;
import groom.him.domain.product.repository.ProductRepository;
import groom.him.domain.search.models.entity.SearchEntity;
import groom.him.domain.search.repository.SearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final SearchRepository searchRepositoy;

    public List<ProductBriefResponse> findSearchProductIndex(String word, MemberEntity member){
        if(member != null) {
            SearchEntity search = SearchEntity.builder()
                    .member(member)
                    .searchWord(word)
                    .build();
            searchRepositoy.save(search);
        }
        System.out.println(member.toString());
        return productRepository.findSearchProductIndex(word).stream()
                .map(ProductBriefResponse::of).collect(Collectors.toList());

    }
}
