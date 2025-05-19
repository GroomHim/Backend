package groom.him.domain.product.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import groom.him.core.common.enums.IsPublic;
import groom.him.domain.category.enums.SortType;
import groom.him.domain.member.models.entity.QMemberEntity;
import groom.him.domain.member.models.entity.QWishEntity;
import groom.him.domain.product.models.dto.response.ProductBriefResponse;
import groom.him.domain.product.models.dto.response.ProductDetailResponse;
import groom.him.domain.product.models.dto.response.ProductResponse;
import groom.him.domain.product.models.dto.response.ProductWithWishResponse;
import groom.him.domain.product.models.entity.QProductEntity;
import groom.him.domain.product.models.entity.QProductExhibitCategoryLinkEntity;
import groom.him.domain.product.models.entity.QProductImgEntity;
import groom.him.domain.product.models.entity.QProductSkinTypeLinkEntity;
import groom.him.domain.product.models.enums.ImgType;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

@RequiredArgsConstructor
public class ProductRepositoryCustomImpl implements ProductRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Slice<ProductWithWishResponse> findMemberWishProductBriefBySkinType(Integer memberId,
        Boolean isSkinType, Pageable pageable) {
        QProductEntity product = QProductEntity.productEntity;
        QWishEntity wish = QWishEntity.wishEntity;
        QMemberEntity member = QMemberEntity.memberEntity;
        QProductSkinTypeLinkEntity productSkinTypeLink = QProductSkinTypeLinkEntity.productSkinTypeLinkEntity;

        int limit = pageable.getPageSize() + 1;
        BooleanBuilder builder = new BooleanBuilder();

        if (isSkinType) {
            builder.and(wish.product.productId.in(
                JPAExpressions
                    .select(productSkinTypeLink.product.productId)
                    .from(productSkinTypeLink)
                    .join(member)
                    .on(member.skinTypeEntity.skinTypeId.eq(
                        productSkinTypeLink.skinType.skinTypeId))
                    .where(member.memberId.eq(memberId))
            ));
        } else {
            builder.and(wish.member.memberId.eq(memberId));
        }

        List<ProductBriefResponse> productBriefList = jpaQueryFactory
            .select(getProductBriefResponseConstructor(product))
            .from(wish)
            .leftJoin(wish.product, product)
            .where(builder, defaultProductCondition(product))
            .orderBy(wish.regDt.asc())
            .offset(pageable.getOffset())
            .limit(limit)
            .fetch();

        List<ProductWithWishResponse> content = getProductWithWishResponses(memberId,
            productBriefList);

        boolean hasNext = isHasNext(pageable, content);

        return new SliceImpl<>(content, pageable, hasNext);
    }

    @Override
    public Slice<ProductWithWishResponse> findRandomProductByCategoryId(Pageable pageable,
        List<Integer> subCategoryList, Integer memberId) {
        QProductEntity product = QProductEntity.productEntity;
        QProductExhibitCategoryLinkEntity productExhibitCategoryLink = QProductExhibitCategoryLinkEntity.productExhibitCategoryLinkEntity;
        QWishEntity wish = QWishEntity.wishEntity;

        int limit = pageable.getPageSize() + 1;

        List<ProductBriefResponse> productBriefList = jpaQueryFactory
            .select(getProductBriefResponseConstructor(product))
            .from(product)
            .leftJoin(productExhibitCategoryLink)
            .on(product.productId.eq(productExhibitCategoryLink.product.productId))
            .leftJoin(wish).on(wish.product.productId.eq(product.productId))
            .where(productExhibitCategoryLink.exhibitCategory.exhibitCategoryId.in(subCategoryList),
                defaultProductCondition(product))
            .orderBy(Expressions.numberTemplate(Double.class, "function('rand')").asc())
            .offset(pageable.getOffset())
            .limit(limit)
            .fetch();

        List<ProductWithWishResponse> content = getProductWithWishResponses(memberId,
            productBriefList);

        boolean hasNext = isHasNext(pageable, content);

        return new SliceImpl<>(content, pageable, hasNext);
    }

    @Override
    public ProductDetailResponse findProductDetailByProductId(Integer memberId, Integer productId) {
        QProductEntity product = QProductEntity.productEntity;

        ProductResponse productResponse = jpaQueryFactory
            .select(getProductResponseConstructor(product))
            .from(product)
            .where(product.productId.eq(productId), defaultProductCondition(product))
            .fetchOne();

        Boolean isWish = IsWishByMemberIdAndProductId(memberId, productId);

        List<String> mainImageList = getProductImageListByImgType(productId, ImgType.MAIN);
        List<String> contentImageList = getProductImageListByImgType(productId, ImgType.CONTENT);

        return new ProductDetailResponse(productResponse, isWish, mainImageList, contentImageList);
    }

    public Slice<ProductWithWishResponse> findProductListByCategoryId(Pageable pageable,
        Integer categoryId, SortType sortType, Integer memberId) {
        QProductEntity product = QProductEntity.productEntity;
        QProductExhibitCategoryLinkEntity productExhibitCategoryLink = QProductExhibitCategoryLinkEntity.productExhibitCategoryLinkEntity;
        QWishEntity wish = QWishEntity.wishEntity;

        int limit = pageable.getPageSize() + 1;

        List<ProductWithWishResponse> content = jpaQueryFactory
            .select(getProductWithWishResponseConstructor(product, wish))
            .from(product)
            .leftJoin(productExhibitCategoryLink)
            .on(product.productId.eq(productExhibitCategoryLink.product.productId))
            .leftJoin(wish).on(wish.product.productId.eq(product.productId))
            .where(productExhibitCategoryLink.exhibitCategory.exhibitCategoryId.eq(categoryId),
                defaultProductCondition(product))
            .groupBy(product.productId)
            .orderBy(orderBySortType(sortType, product, wish))
            .offset(pageable.getOffset())
            .limit(limit)
            .fetch();

        boolean hasNext = isHasNext(pageable, content);

        return new SliceImpl<>(content, pageable, hasNext);
    }

    @Override
    public Slice<ProductWithWishResponse> findProductListByBrand(Pageable pageable,
        String brandName, Integer memberId) {
        QProductEntity product = QProductEntity.productEntity;
        QWishEntity wish = QWishEntity.wishEntity;

        int limit = pageable.getPageSize() + 1;

        List<ProductWithWishResponse> content = jpaQueryFactory
            .select(getProductWithWishResponseConstructor(product, wish))
            .from(product)
            .leftJoin(wish).on(wish.product.productId.eq(product.productId))
            .where(product.brand.enBrandName.eq(brandName), defaultProductCondition(product))
            .offset(pageable.getOffset())
            .limit(limit)
            .fetch();

        boolean hasNext = isHasNext(pageable, content);

        return new SliceImpl<>(content, pageable, hasNext);
    }

    @Override
    public List<ProductWithWishResponse> findProductListBySearchWord(String word,
        Integer memberId, SortType sortType) {
        QProductEntity product = QProductEntity.productEntity;
        QWishEntity wish = QWishEntity.wishEntity;

        return jpaQueryFactory
            .select(getProductWithWishResponseConstructor(product, wish))
            .from(product)
            .leftJoin(wish).on(wish.product.productId.eq(product.productId))
            .where(product.productName.contains(word), defaultProductCondition(product)) // %{word}%
            .orderBy(orderBySortType(sortType, product, wish))
            .fetch();
    }

    @Override
    public Slice<ProductWithWishResponse> findProductListBySkinType(
        Pageable pageable, Integer skinTypeId, Integer memberId) {
        QProductEntity product = QProductEntity.productEntity;
        QProductSkinTypeLinkEntity productSkinTypeLink = QProductSkinTypeLinkEntity.productSkinTypeLinkEntity;

        int limit = pageable.getPageSize() + 1;

        List<ProductBriefResponse> productBriefList = jpaQueryFactory
            .select(getProductBriefResponseConstructor(product))
            .from(product)
            .leftJoin(productSkinTypeLink)
            .on(productSkinTypeLink.product.productId.eq(product.productId))
            .where(productSkinTypeLink.skinType.skinTypeId.eq(skinTypeId),
                defaultProductCondition(product))
            .groupBy(product.productId)
            .offset(pageable.getOffset())
            .limit(limit)
            .fetch();

        List<ProductWithWishResponse> content = getProductWithWishResponses(memberId,
            productBriefList);

        boolean hasNext = isHasNext(pageable, content);

        return new SliceImpl<>(content, pageable, hasNext);
    }

    @Override
    public Slice<ProductWithWishResponse> findProductListByPriceRange(Pageable pageable,
        Integer minPrice, Integer maxPrice, Integer memberId) {
        QProductEntity product = QProductEntity.productEntity;

        int limit = pageable.getPageSize() + 1;

        List<ProductBriefResponse> productBriefList = jpaQueryFactory
            .select(getProductBriefResponseConstructor(product))
            .from(product)
            .where(product.discountedPrice.between(minPrice, maxPrice),
                defaultProductCondition(product))
            .groupBy(product.productId)
            .offset(pageable.getOffset())
            .limit(limit)
            .fetch();

        List<ProductWithWishResponse> content = getProductWithWishResponses(memberId,
            productBriefList);

        boolean hasNext = isHasNext(pageable, content);

        return new SliceImpl<>(content, pageable, hasNext);
    }

    private List<ProductWithWishResponse> getProductWithWishResponses(Integer memberId,
        List<ProductBriefResponse> productBriefList) {
        List<Integer> wishProductIdList = getWishProductIdByMemberId(memberId);
        List<ProductWithWishResponse> content = new ArrayList<>();

        productBriefList.forEach((productBriefResponse) -> {
            boolean isWished = wishProductIdList.contains(productBriefResponse.productId());
            content.add(new ProductWithWishResponse(productBriefResponse, isWished));
        });

        return content;
    }

    private boolean isHasNext(Pageable pageable, List<?> content) {
        boolean hasNext = false;
        if (content.size() > pageable.getPageSize()) {
            hasNext = true;
            content.remove(pageable.getPageSize());
        }
        return hasNext;
    }

    private Expression<Boolean> isProductWished(QWishEntity wish) {
        return wish.product.productId.isNotNull();
    }

    private ConstructorExpression<ProductWithWishResponse> getProductWithWishResponseConstructor(
        QProductEntity product, QWishEntity wish) {
        return Projections.constructor(
            ProductWithWishResponse.class,
            getProductBriefResponseConstructor(product), isProductWished(wish)
        );
    }

    private OrderSpecifier<?> orderBySortType(SortType sortType, QProductEntity product,
        QWishEntity wish) {
        return switch (sortType) {
            case SALE -> product.discountRate.desc(); // FIXME
            case WISH -> wish.count().desc();
            case HIGH_PRICE -> product.discountedPrice.desc();
            case LOW_PRICE -> product.discountedPrice.asc();
            case DISCOUNT_RATE -> product.discountRate.desc();
        };
    }

    private ConstructorExpression<ProductBriefResponse> getProductBriefResponseConstructor(
        QProductEntity product) {
        return Projections.constructor(
            ProductBriefResponse.class,
            product.productId, product.category.categoryName, product.productName,
            product.brand.brandName, product.price,
            product.discountRate, product.discountedPrice, product.imgUrl
        );
    }

    private ConstructorExpression<ProductResponse> getProductResponseConstructor(
        QProductEntity product) {
        return Projections.constructor(
            ProductResponse.class,
            product.productId, product.productName, product.price, product.discountRate,
            product.discountedPrice, product.brand.brandName, product.ingredients,
            product.purchaseSiteUrl
        );
    }

    private List<String> getProductImageListByImgType(Integer productId, ImgType imgType) {
        QProductImgEntity productImg = QProductImgEntity.productImgEntity;

        return jpaQueryFactory
            .select(productImg.imgUrl)
            .from(productImg)
            .where(productImg.product.productId.eq(productId).and(productImg.type.eq(imgType)))
            .orderBy(productImg.prio.asc())
            .fetch();
    }

    private Boolean IsWishByMemberIdAndProductId(Integer memberId, Integer productId) {
        QWishEntity wish = QWishEntity.wishEntity;

        return jpaQueryFactory
            .selectOne()
            .from(wish)
            .where(wish.product.productId.eq(productId).and(wish.member.memberId.eq(memberId)))
            .fetchFirst() != null;
    }

    private List<Integer> getWishProductIdByMemberId(Integer memberId) {
        QWishEntity wish = QWishEntity.wishEntity;

        return jpaQueryFactory
            .select(wish.product.productId)
            .from(wish)
            .where(wish.member.memberId.eq(memberId))
            .fetch();
    }

    private BooleanExpression defaultProductCondition(QProductEntity product) {
        return product.isDeleted.eq(false).and(product.isPublic.eq(IsPublic.OPEN));
    }
}