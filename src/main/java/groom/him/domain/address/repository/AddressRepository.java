package groom.him.domain.address.repository;

import groom.him.domain.address.models.entity.AddressEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<AddressEntity, Integer> {
    @Query(value = "select a from AddressEntity a where a.member.memberId = :memberId order by a.regDt desc")
    List<AddressEntity> findAllByMemberId(@Param("memberId") Integer memberId);
}