package groom.him.domain.product.service;

import groom.him.domain.category.enums.SortType;
import groom.him.domain.category.repository.ExhibitCategoryRepository;
import groom.him.domain.member.models.entity.MemberEntity;
import groom.him.domain.product.exception.ProductErrorCode;
import groom.him.domain.product.exception.ProductException;
import groom.him.domain.product.models.dto.response.ProductBriefResponse;
import groom.him.domain.product.models.dto.response.ProductWithWishResponse;
import groom.him.domain.product.models.entity.ProductEntity;
import groom.him.domain.product.repository.ProductRepository;
import groom.him.domain.search.models.entity.SearchEntity;
import groom.him.domain.search.repository.SearchRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private static final int RECENT_WORD_CNT = 5;

    private final ProductRepository productRepository;
    private final ExhibitCategoryRepository exhibitCategoryRepository;
    private final SearchRepository searchRepository;

    public ProductEntity findById(Integer productId) {
        return productRepository.findById(productId).orElseThrow(
            () -> new ProductException(ProductErrorCode.PRODUCT_NOT_EXIST));
    }

    public Slice<ProductWithWishResponse> findRandomProductBrief(Pageable pageable,
        List<Integer> categoryIdList) {
        List<Integer> subCategoryIdList = exhibitCategoryRepository.getLeafCategoryIdByTargetCategoryId(
            categoryIdList);
        return productRepository.findRandomProductByCategoryId(pageable, subCategoryIdList);
    }

    public Slice<ProductWithWishResponse> findRecommendProductBriefBySkinType(Pageable pageable,
        Integer skinTypeId) {
        return productRepository.findProductListBySkinTypeOrderByQuantity(pageable, skinTypeId);
    }

    public Slice<ProductWithWishResponse> findProductBriefByPrice(Pageable pageable,
        Integer minPrice, Integer maxPrice) {
        return productRepository.findProductListByPriceRange(pageable, minPrice, maxPrice);
    }

    public List<ProductBriefResponse> findSearchProductIndex(String word, MemberEntity member) {
        if (searchRepository.countByMember_MemberId(member.getMemberId()) < RECENT_WORD_CNT) {
            SearchEntity search = SearchEntity.builder()
                .member(member)
                .searchWord(word)
                .build();
            searchRepository.save(search);
        }
        List<ProductBriefResponse> list = new ArrayList<>();
        productRepository.findSearchProductIndex(word).stream()
            .map(ProductBriefResponse::of).forEach(list::add);
        return list;
    }

    public Slice<ProductWithWishResponse> findProductListByCategory(Pageable pageable,
        Integer categoryId, SortType sortType, Integer memberId) {
        return productRepository.findProductListByCategoryId(pageable, categoryId, sortType,
            memberId);
    }

    public Slice<ProductWithWishResponse> findMemberProductWishList(Integer memberId,
        Boolean isSkinType,
        Pageable pageable) {
        return productRepository.findMemberWishProductBriefBySkinType(memberId, isSkinType,
            pageable);
    }
}