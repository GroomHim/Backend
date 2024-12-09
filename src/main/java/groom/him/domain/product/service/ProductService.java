package groom.him.domain.product.service;

import groom.him.domain.member.models.entity.MemberEntity;
import groom.him.domain.product.models.dto.response.ProductBriefResponse;
import groom.him.domain.product.repository.ProductRepository;
import groom.him.domain.search.models.entity.SearchEntity;
import groom.him.domain.search.repository.SearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import groom.him.domain.category.repository.ExhibitCategoryRepository;
import groom.him.domain.product.models.dto.request.RandomProductRequest;
import groom.him.domain.product.models.entity.ProductEntity;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private static final int RECENT_WORD_CNT = 5;

    private final ProductRepository productRepository;
    private final ExhibitCategoryRepository exhibitCategoryRepository;
    private final SearchRepository searchRepository;

    public Slice<ProductBriefResponse> findRandomProductBrief(Pageable pageable,
        RandomProductRequest request) {
        List<Integer> subCategoryIdList = exhibitCategoryRepository.getLeafCategoryIdByTargetCategoryId(
            request.categoryIdList());
        List<ProductEntity> productEntityList = productRepository.findRandomProductEntitiesByCategoryId(
            pageable.getPageSize() + 1,
            (int) pageable.getOffset(), subCategoryIdList);

        boolean hasNext = false;
        if (productEntityList.size() > pageable.getPageSize()) {
            hasNext = true;
            productEntityList.removeLast();
        }

        return new SliceImpl<>(productEntityList.stream().map(ProductBriefResponse::of).toList(),
            pageable, hasNext);
    }

    public Slice<ProductBriefResponse> findRecommendProductBriefBySkinType(Pageable pageable,
        Integer skinTypeId) {
        return productRepository.findProductListBySkinTypeOrderByQuantity(pageable, skinTypeId)
            .map(ProductBriefResponse::of);
    }

    public Slice<ProductBriefResponse> findProductBriefByPrice(Pageable pageable, Integer minPrice,
        Integer maxPrice) {
        return productRepository.findProductListByPriceRange(pageable, minPrice, maxPrice)
            .map(ProductBriefResponse::of);
    }

    public List<ProductBriefResponse> findSearchProductIndex(String word, MemberEntity member){
        if(searchRepository.countByMember(member) < RECENT_WORD_CNT) {
            SearchEntity search = SearchEntity.builder()
                    .member(member)
                    .searchWord(word)
                    .build();
            searchRepository.save(search);
        }
        return productRepository.findSearchProductIndex(word).stream()
            .map(ProductBriefResponse::of).collect(Collectors.toList());

    }
}
