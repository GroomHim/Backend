package groom.him.domain.product.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import groom.him.domain.member.models.entity.QMemberEntity;
import groom.him.domain.member.models.entity.QWishEntity;
import groom.him.domain.order.models.entity.QOrderDetailEntity;
import groom.him.domain.product.models.dto.response.ProductBriefResponse;
import groom.him.domain.product.models.dto.response.ProductWithWishResponse;
import groom.him.domain.product.models.entity.QProductEntity;
import groom.him.domain.product.models.entity.QProductExhibitCategoryLinkEntity;
import groom.him.domain.product.models.entity.QProductSkinTypeLinkEntity;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

@RequiredArgsConstructor
public class ProductRepositoryCustomImpl implements ProductRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;
    private static QProductEntity product = QProductEntity.productEntity;
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

        List<ProductWithWishResponse> content = jpaQueryFactory
            .select(Projections.constructor(
                ProductWithWishResponse.class,
                Projections.constructor(
                    ProductBriefResponse.class,
                    product.productId, product.productName, product.price, product.discountRate,
                    product.discountedPrice, product.imgUrl
                ), isProductWished(wish)
            ))
            .from(wish)
            .leftJoin(wish.product, product)
            .where(builder)
            .orderBy(wish.regDt.asc())
            .offset(pageable.getOffset())
            .limit(limit)
            .fetch();

        boolean hasNext = isHasNext(pageable, content);

        return new SliceImpl<>(content, pageable, hasNext);
    }

    @Override
    public Slice<ProductWithWishResponse> findRandomProductByCategoryId(Pageable pageable,
        List<Integer> target) {
        QProductEntity product = QProductEntity.productEntity;
        QProductExhibitCategoryLinkEntity productExhibitCategoryLink = QProductExhibitCategoryLinkEntity.productExhibitCategoryLinkEntity;
        QWishEntity wish = QWishEntity.wishEntity;

        int limit = pageable.getPageSize() + 1;

        List<ProductWithWishResponse> content = jpaQueryFactory
            .select(Projections.constructor(
                ProductWithWishResponse.class,
                Projections.constructor(
                    ProductBriefResponse.class,
                    product.productId, product.productName, product.price, product.discountRate,
                    product.discountedPrice, product.imgUrl
                ), isProductWished(wish)
            ))
            .from(product)
            .leftJoin(productExhibitCategoryLink)
            .on(product.productId.eq(productExhibitCategoryLink.product.productId))
            .leftJoin(wish).on(wish.product.productId.eq(product.productId))
            .where(productExhibitCategoryLink.product.productId.in(target))
            .orderBy(Expressions.numberTemplate(Double.class, "function('rand')").asc())
            .offset(pageable.getOffset())
            .limit(limit)
            .fetch();

        boolean hasNext = isHasNext(pageable, content);

        return new SliceImpl<>(content, pageable, hasNext);
    }

    @Override
    public Slice<ProductWithWishResponse> findProductListBySkinTypeOrderByQuantity(
        Pageable pageable, Integer skinType) {
        QProductEntity product = QProductEntity.productEntity;
        QProductSkinTypeLinkEntity productSkinTypeLink = QProductSkinTypeLinkEntity.productSkinTypeLinkEntity;
        QOrderDetailEntity orderDetail = QOrderDetailEntity.orderDetailEntity;
        QWishEntity wish = QWishEntity.wishEntity;

        int limit = pageable.getPageSize() + 1;

        List<ProductWithWishResponse> content = jpaQueryFactory
            .select(Projections.constructor(
                ProductWithWishResponse.class,
                Projections.constructor(
                    ProductBriefResponse.class,
                    product.productId, product.productName, product.price, product.discountRate,
                    product.discountedPrice, product.imgUrl
                ), isProductWished(wish)
            ))
            .from(product)
            .join(productSkinTypeLink)
            .on(productSkinTypeLink.product.productId.eq(product.productId))
            .leftJoin(orderDetail).on(orderDetail.product.productId.eq(product.productId))
            .leftJoin(wish).on(wish.product.productId.eq(product.productId))
            .where(productSkinTypeLink.skinType.skinTypeId.eq(skinType))
            .groupBy(product.productId)
            .orderBy(orderBySaleQuantity(orderDetail))
            .offset(pageable.getOffset())
            .limit(limit)
            .fetch();

        boolean hasNext = isHasNext(pageable, content);

        return new SliceImpl<>(content, pageable, hasNext);
    }

    @Override
    public Slice<ProductWithWishResponse> findProductListByPriceRange(Pageable pageable,
        Integer minPrice, Integer maxPrice) {
        QProductEntity product = QProductEntity.productEntity;
        QOrderDetailEntity orderDetail = QOrderDetailEntity.orderDetailEntity;
        QWishEntity wish = QWishEntity.wishEntity;

        int limit = pageable.getPageSize() + 1;

        List<ProductWithWishResponse> content = jpaQueryFactory
            .select(Projections.constructor(
                ProductWithWishResponse.class,
                Projections.constructor(
                    ProductBriefResponse.class,
                    product.productId, product.productName, product.price, product.discountRate,
                    product.discountedPrice, product.imgUrl
                ), isProductWished(wish)
            ))
            .from(product)
            .join(orderDetail).on(orderDetail.product.productId.eq(product.productId))
            .leftJoin(wish).on(wish.product.productId.eq(product.productId))
            .where(product.discountedPrice.between(minPrice, maxPrice))
            .groupBy(product.productId)
            .orderBy(orderBySaleQuantity(orderDetail))
            .offset(pageable.getOffset())
            .limit(limit)
            .fetch();

        boolean hasNext = isHasNext(pageable, content);

        return new SliceImpl<>(content, pageable, hasNext);
    }

    private Expression<Boolean> isProductWished(QWishEntity wish) {
        return wish.product.productId.isNotNull();
    }

    private boolean isHasNext(Pageable pageable, List<?> content) {
        boolean hasNext = false;
        if (content.size() > pageable.getPageSize()) {
            hasNext = true;
            content.remove(pageable.getPageSize());
        }
        return hasNext;
    }

    private OrderSpecifier<Integer> orderBySaleQuantity(QOrderDetailEntity orderDetail) {
        return orderDetail.quantity.sum().desc();
    }
}