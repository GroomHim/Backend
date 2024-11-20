package groom.him.core.model.banword.repository;

import groom.him.domain.member.models.entity.BanwordEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BanwordRepository extends JpaRepository<BanwordEntity, Integer> {
    Optional<BanwordEntity> findByBanword(String banword);
}
