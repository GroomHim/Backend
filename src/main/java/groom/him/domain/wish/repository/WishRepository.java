package groom.him.domain.wish.repository;

import groom.him.domain.member.models.entity.WishEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishRepository extends JpaRepository<WishEntity, Integer> {
    Integer countDistinctByMember_MemberId(Integer memberId);

    boolean existsByMember_MemberIdAndProduct_ProductId(Integer memberId, Integer productId);

    void deleteByMember_MemberIdAndProduct_ProductId(Integer memberId, Integer productId);
}