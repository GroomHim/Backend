package groom.him.core.domain.notice.repository;

import groom.him.core.common.enums.IsPublic;
import groom.him.core.domain.notice.models.entities.NoticeEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeRepository extends JpaRepository<NoticeEntity, Long> {
    List<NoticeEntity> findAllByIsPublicOrderByRegDtDesc(IsPublic isPublic);
}