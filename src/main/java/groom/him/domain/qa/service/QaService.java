package groom.him.domain.qa.service;

import groom.him.core.model.member.exception.MemberErrorCode;
import groom.him.core.model.member.exception.MemberException;
import groom.him.core.model.member.repository.MemberRepository;
import groom.him.domain.member.models.entity.MemberEntity;
import groom.him.domain.qa.exception.QaException;
import groom.him.domain.qa.models.dto.request.QaRequest;
import groom.him.domain.qa.models.dto.response.QaCategoryResponse;
import groom.him.domain.qa.models.entity.QaCategoryEntity;
import groom.him.domain.qa.models.entity.QaEntity;
import groom.him.domain.qa.models.enums.QaErrorCode;
import groom.him.domain.qa.repository.QaCategoryRepository;
import groom.him.domain.qa.repository.QaRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class QaService {
    private final QaRepository qaRepository;
    private final QaCategoryRepository qaCategoryRepository;
    private final MemberRepository memberRepository;

    public QaService(QaRepository qaRepository, QaCategoryRepository qaCategoryRepository,
        MemberRepository memberRepository) {
        this.qaRepository = qaRepository;
        this.qaCategoryRepository = qaCategoryRepository;
        this.memberRepository = memberRepository;
    }

    public List<QaCategoryResponse> findQaCategoryList() {
        return qaCategoryRepository.findAllByParentQaCategoryIsNull().stream()
            .map(category -> QaCategoryResponse.of(
                category.getQaCategoryName(),
                category.getChildren().stream()
                    .map(QaCategoryEntity::getQaCategoryName)
                    .collect(Collectors.toList()))
            ).collect(Collectors.toList());
    }

    public void addQa(QaRequest request, Integer memberId) {
        MemberEntity member = memberRepository.findById(memberId)
            .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_EXIST));
        QaCategoryEntity qaCategory = qaCategoryRepository.findByQaCategoryId(request.categoryId())
            .orElseThrow(() -> new QaException(QaErrorCode.QA_CATEGORY_NOT_EXIST));
        qaRepository.save(QaEntity.builder().member(member).qaCategory(qaCategory)
            .title(request.title()).content(request.content()).build());
    }
}