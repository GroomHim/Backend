//package groom.v1.product;
//
//import com.querydsl.core.BooleanBuilder;
//import com.querydsl.core.types.ConstructorExpression;
//import com.querydsl.core.types.Expression;
//import com.querydsl.core.types.OrderSpecifier;
//import com.querydsl.core.types.Projections;
//import com.querydsl.core.types.dsl.Expressions;
//import com.querydsl.jpa.JPAExpressions;
//import com.querydsl.jpa.impl.JPAQueryFactory;
//import groom.him.domain.category.enums.SortType;
//import groom.him.domain.member.models.entity.QMemberEntity;
//import groom.him.domain.member.models.entity.QWishEntity;
//import groom.him.domain.order.models.entity.QOrderDetailEntity;
//import groom.him.domain.product.models.dto.response.ProductBriefResponse;
//import groom.him.domain.product.models.dto.response.ProductDetailResponse;
//import groom.him.domain.product.models.dto.response.ProductResponse;
//import groom.him.domain.product.models.dto.response.ProductWithWishResponse;
//import groom.him.domain.product.models.entity.QProductEntity;
//import groom.him.domain.product.models.entity.QProductExhibitCategoryLinkEntity;
//import groom.him.domain.product.models.entity.QProductImgEntity;
//import groom.him.domain.product.models.entity.QProductSkinTypeLinkEntity;
//import groom.him.domain.product.models.enums.ImgType;
//import groom.him.domain.product.repository.ProductRepositoryCustom;
//import java.util.ArrayList;
//import java.util.List;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Slice;
//import org.springframework.data.domain.SliceImpl;
//
//@RequiredArgsConstructor
//public class ProductRepositoryCustomImpl implements ProductRepositoryCustom {
//    private final JPAQueryFactory jpaQueryFactory;
//
//    @Override
//    public Slice<ProductWithWishResponse> findMemberWishProductBriefBySkinType(Integer memberId,
//        Boolean isSkinType, Pageable pageable) {
//        QProductEntity product = QProductEntity.productEntity;
//        QWishEntity wish = QWishEntity.wishEntity;
//        QMemberEntity member = QMemberEntity.memberEntity;
//        QProductSkinTypeLinkEntity productSkinTypeLink = QProductSkinTypeLinkEntity.productSkinTypeLinkEntity;
//
//        int limit = pageable.getPageSize() + 1;
//        BooleanBuilder builder = new BooleanBuilder();
//
//        if (isSkinType) {
//            builder.and(wish.product.productId.in(
//                JPAExpressions
//                    .select(productSkinTypeLink.product.productId)
//                    .from(productSkinTypeLink)
//                    .join(member)
//                    .on(member.skinTypeEntity.skinTypeId.eq(
//                        productSkinTypeLink.skinType.skinTypeId))
//                    .where(member.memberId.eq(memberId))
//            ));
//        } else {
//            builder.and(wish.member.memberId.eq(memberId));
//        }
//
//        List<ProductBriefResponse> productBriefList = jpaQueryFactory
//            .select(getProductBriefResponseConstructor(product))
//            .from(wish)
//            .leftJoin(wish.product, product)
//            .where(builder)
//            .orderBy(wish.regDt.asc())
//            .offset(pageable.getOffset())
//            .limit(limit)
//            .fetch();
//
//        List<ProductWithWishResponse> content = getProductWithWishResponses(memberId,
//            productBriefList);
//
//        boolean hasNext = isHasNext(pageable, content);
//
//        return new SliceImpl<>(content, pageable, hasNext);
//    }
//
//    @Override
//    public Slice<ProductWithWishResponse> findRandomProductByCategoryId(Pageable pageable,
//        List<Integer> subCategoryList, Integer memberId) {
//        QProductEntity product = QProductEntity.productEntity;
//        QProductExhibitCategoryLinkEntity productExhibitCategoryLink = QProductExhibitCategoryLinkEntity.productExhibitCategoryLinkEntity;
//        QWishEntity wish = QWishEntity.wishEntity;
//
//        int limit = pageable.getPageSize() + 1;
//
//        List<ProductBriefResponse> productBriefList = jpaQueryFactory
//            .select(getProductBriefResponseConstructor(product))
//            .from(product)
//            .leftJoin(productExhibitCategoryLink)
//            .on(product.productId.eq(productExhibitCategoryLink.product.productId))
//            .leftJoin(wish).on(wish.product.productId.eq(product.productId))
//            .where(productExhibitCategoryLink.exhibitCategory.exhibitCategoryId.in(subCategoryList))
//            .orderBy(Expressions.numberTemplate(Double.class, "function('rand')").asc())
//            .offset(pageable.getOffset())
//            .limit(limit)
//            .fetch();
//
//        List<ProductWithWishResponse> content = getProductWithWishResponses(memberId,
//            productBriefList);
//
//        boolean hasNext = isHasNext(pageable, content);
//
//        return new SliceImpl<>(content, pageable, hasNext);
//    }
//
//    @Override
//    public ProductDetailResponse findProductDetailByProductId(Integer memberId, Integer productId) {
//        QProductEntity product = QProductEntity.productEntity;
//
//        ProductResponse productResponse = jpaQueryFactory
//            .select(getProductResponseConstructor(product))
//            .from(product)
//            .where(product.productId.eq(productId))
//            .fetchOne();
//
//        Boolean isWish = IsWishByMemberIdAndProductId(memberId, productId);
//
//        List<String> mainImageList = getProductImageListByImgType(productId, ImgType.MAIN);
//        List<String> contentImageList = getProductImageListByImgType(productId, ImgType.CONTENT);
//
//        return new ProductDetailResponse(productResponse, isWish, mainImageList, contentImageList);
//    }
//
//    public Slice<ProductWithWishResponse> findProductListByCategoryId(Pageable pageable,
//        Integer categoryId, SortType sortType, Integer memberId) {
//        QProductEntity product = QProductEntity.productEntity;
//        QProductExhibitCategoryLinkEntity productExhibitCategoryLink = QProductExhibitCategoryLinkEntity.productExhibitCategoryLinkEntity;
//        QWishEntity wish = QWishEntity.wishEntity;
//        QOrderDetailEntity orderDetail = QOrderDetailEntity.orderDetailEntity;
//
//        int limit = pageable.getPageSize() + 1;
//
//        List<ProductBriefResponse> productBriefList = jpaQueryFactory
//            .select(getProductBriefResponseConstructor(product))
//            .from(product)
//            .leftJoin(productExhibitCategoryLink)
//            .on(product.productId.eq(productExhibitCategoryLink.product.productId))
//            .leftJoin(wish).on(wish.product.productId.eq(product.productId))
//            .leftJoin(orderDetail).on(orderDetail.product.productId.eq(product.productId))
//            .where(productExhibitCategoryLink.exhibitCategory.exhibitCategoryId.eq(categoryId))
//            .groupBy(product.productId)
//            .orderBy(orderBySortType(sortType, orderDetail, wish, product))
//            .offset(pageable.getOffset())
//            .limit(limit)
//            .fetch();
//
//        List<ProductWithWishResponse> content = getProductWithWishResponses(memberId,
//            productBriefList);
//
//        boolean hasNext = isHasNext(pageable, content);
//
//        return new SliceImpl<>(content, pageable, hasNext);
//    }
//
//    @Override
//    public Slice<ProductWithWishResponse> findProductListByBrand(Pageable pageable,
//        String brandName, Integer memberId) {
//        QProductEntity product = QProductEntity.productEntity;
//        QWishEntity wish = QWishEntity.wishEntity;
//
//        int limit = pageable.getPageSize() + 1;
//
//        List<ProductWithWishResponse> content = jpaQueryFactory
//            .select(getProductWithWishResponseConstructor(product, wish))
//            .from(product)
//            .leftJoin(wish).on(wish.product.productId.eq(product.productId))
//            .where(product.brand.enBrandName.eq(brandName))
//            .offset(pageable.getOffset())
//            .limit(limit)
//            .fetch();
//
//        boolean hasNext = isHasNext(pageable, content);
//
//        return new SliceImpl<>(content, pageable, hasNext);
//    }
//
//    @Override
//    public Slice<ProductWithWishResponse> findProductListBySkinTypeOrderByQuantity(
//        Pageable pageable, Integer skinType, Integer memberId) {
//        QProductEntity product = QProductEntity.productEntity;
//        QProductSkinTypeLinkEntity productSkinTypeLink = QProductSkinTypeLinkEntity.productSkinTypeLinkEntity;
//        QOrderDetailEntity orderDetail = QOrderDetailEntity.orderDetailEntity;
//        QWishEntity wish = QWishEntity.wishEntity;
//
//        int limit = pageable.getPageSize() + 1;
//
//        List<ProductBriefResponse> productBriefList = jpaQueryFactory
//            .select(getProductBriefResponseConstructor(product))
//            .from(product)
//            .join(productSkinTypeLink)
//            .on(productSkinTypeLink.product.productId.eq(product.productId))
//            .leftJoin(orderDetail).on(orderDetail.product.productId.eq(product.productId))
//            .leftJoin(wish).on(wish.product.productId.eq(product.productId))
//            .where(productSkinTypeLink.skinType.skinTypeId.eq(skinType))
//            .groupBy(product.productId)
//            .orderBy(orderBySaleQuantity(orderDetail))
//            .offset(pageable.getOffset())
//            .limit(limit)
//            .fetch();
//
//        List<ProductWithWishResponse> content = getProductWithWishResponses(memberId,
//            productBriefList);
//
//        boolean hasNext = isHasNext(pageable, content);
//
//        return new SliceImpl<>(content, pageable, hasNext);
//    }
//
//    @Override
//    public Slice<ProductWithWishResponse> findProductListByPriceRange(Pageable pageable,
//        Integer minPrice, Integer maxPrice, Integer memberId) {
//        QProductEntity product = QProductEntity.productEntity;
//        QOrderDetailEntity orderDetail = QOrderDetailEntity.orderDetailEntity;
//        QWishEntity wish = QWishEntity.wishEntity;
//
//        int limit = pageable.getPageSize() + 1;
//
//        List<ProductBriefResponse> productBriefList = jpaQueryFactory
//            .select(getProductBriefResponseConstructor(product))
//            .from(product)
//            .leftJoin(orderDetail).on(orderDetail.product.productId.eq(product.productId))
//            .leftJoin(wish).on(wish.product.productId.eq(product.productId))
//            .where(product.discountedPrice.between(minPrice, maxPrice))
//            .groupBy(product.productId)
//            .orderBy(orderBySaleQuantity(orderDetail))
//            .offset(pageable.getOffset())
//            .limit(limit)
//            .fetch();
//
//        List<ProductWithWishResponse> content = getProductWithWishResponses(memberId,
//            productBriefList);
//
//        boolean hasNext = isHasNext(pageable, content);
//
//        return new SliceImpl<>(content, pageable, hasNext);
//    }
//
//    private List<ProductWithWishResponse> getProductWithWishResponses(Integer memberId,
//        List<ProductBriefResponse> productBriefList) {
//        List<Integer> wishProductIdList = getWishProductIdByMemberId(memberId);
//        List<ProductWithWishResponse> content = new ArrayList<>();
//
//        productBriefList.forEach((productBriefResponse) -> {
//            boolean isWished = wishProductIdList.contains(productBriefResponse.productId());
//            content.add(new ProductWithWishResponse(productBriefResponse, isWished));
//        });
//
//        return content;
//    }
//
//    private boolean isHasNext(Pageable pageable, List<?> content) {
//        boolean hasNext = false;
//        if (content.size() > pageable.getPageSize()) {
//            hasNext = true;
//            content.remove(pageable.getPageSize());
//        }
//        return hasNext;
//    }
//
//    private Expression<Boolean> isProductWished(QWishEntity wish) {
//        return wish.product.productId.isNotNull();
//    }
//
//    private ConstructorExpression<ProductWithWishResponse> getProductWithWishResponseConstructor(
//        QProductEntity product, QWishEntity wish) {
//        return Projections.constructor(
//            ProductWithWishResponse.class,
//            getProductBriefResponseConstructor(product), isProductWished(wish)
//        );
//    }
//
//    private OrderSpecifier<Integer> orderBySaleQuantity(QOrderDetailEntity orderDetail) {
//        return orderDetail.quantity.sum().desc();
//    }
//
//    private OrderSpecifier<Integer> orderByHighPrice(QProductEntity product) {
//        return product.discountedPrice.desc();
//    }
//
//    private OrderSpecifier<Integer> orderByLowPrice(QProductEntity product) {
//        return product.discountedPrice.asc();
//    }
//
//    private OrderSpecifier<Float> orderByDiscountRate(QProductEntity product) {
//        return product.discountRate.desc();
//    }
//
//    private OrderSpecifier<Long> orderByWish(QWishEntity wish) {
//        return wish.count().desc();
//    }
//
//    private OrderSpecifier<?> orderBySortType(SortType sortType,
//        QOrderDetailEntity orderDetail, QWishEntity wish, QProductEntity product) {
//        return switch (sortType) {
//            case SALE -> orderBySaleQuantity(orderDetail);
//            case WISH -> orderByWish(wish);
//            case HIGH_PRICE -> orderByHighPrice(product);
//            case LOW_PRICE -> orderByLowPrice(product);
//            case DISCOUNT_RATE -> orderByDiscountRate(product);
//        };
//    }
//
//    private ConstructorExpression<ProductBriefResponse> getProductBriefResponseConstructor(
//        QProductEntity product) {
//        return Projections.constructor(
//            ProductBriefResponse.class,
//            product.productId, product.productName, product.brand.brandName, product.price,
//            product.discountRate, product.discountedPrice, product.imgUrl
//        );
//    }
//
//    private ConstructorExpression<ProductResponse> getProductResponseConstructor(
//        QProductEntity product) {
//        return Projections.constructor(
//            ProductResponse.class,
//            product.productId, product.productName, product.price, product.discountRate,
//            product.discountedPrice, product.brand.brandName, product.ingredients,
//            product.deliveryInfo, product.purchaseSiteUrl
//        );
//    }
//
//    private List<String> getProductImageListByImgType(Integer productId, ImgType imgType) {
//        QProductImgEntity productImg = QProductImgEntity.productImgEntity;
//
//        return jpaQueryFactory
//            .select(productImg.imgUrl)
//            .from(productImg)
//            .where(productImg.product.productId.eq(productId).and(productImg.type.eq(imgType)))
//            .orderBy(productImg.prio.asc())
//            .fetch();
//    }
//
//    private Boolean IsWishByMemberIdAndProductId(Integer memberId, Integer productId) {
//        QWishEntity wish = QWishEntity.wishEntity;
//
//        return jpaQueryFactory
//            .selectOne()
//            .from(wish)
//            .where(wish.product.productId.eq(productId).and(wish.member.memberId.eq(memberId)))
//            .fetchFirst() != null;
//    }
//
//    private List<Integer> getWishProductIdByMemberId(Integer memberId) {
//        QWishEntity wish = QWishEntity.wishEntity;
//
//        return jpaQueryFactory
//            .select(wish.product.productId)
//            .from(wish)
//            .where(wish.member.memberId.eq(memberId))
//            .fetch();
//    }
//}