package groom.him.domain.notice.repository;

import groom.him.core.common.enums.IsPublic;
import groom.him.domain.notice.models.entity.NoticeEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeRepository extends JpaRepository<NoticeEntity, Long> {

    List<NoticeEntity> findAllByIsPublicOrderByRegDtDesc(IsPublic isPublic);
}