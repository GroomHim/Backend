package groom.him.domain.member.service;

import groom.him.core.model.member.exception.MemberErrorCode;
import groom.him.core.model.member.exception.MemberException;
import groom.him.core.model.member.repository.MemberRepository;
import groom.him.domain.product.models.dto.response.ProductBriefResponse;
import groom.him.domain.product.repository.ProductRepository;
import groom.him.domain.qa.models.dto.response.QaResponse;
import groom.him.domain.qa.models.enums.QaStatus;
import groom.him.domain.qa.repository.QaRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;
    private final QaRepository qaRepository;

    public MemberService(MemberRepository memberRepository, ProductRepository productRepository,
        QaRepository qaRepository) {
        this.memberRepository = memberRepository;
        this.productRepository = productRepository;
        this.qaRepository = qaRepository;
    }

    public void checkMemberValidationById(Integer memberId) {
        memberRepository.findByMemberIdAndIsCancelFalse(memberId)
            .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_EXIST));
    }

    public List<ProductBriefResponse> findMemberWishList(Integer memberId, Boolean isSkinType) {
        return productRepository.findMemberWishProductBriefBySkinType(memberId, isSkinType).stream()
            .map(ProductBriefResponse::of).collect(Collectors.toList());
    }

    public List<QaResponse> findMemberQaList(Integer memberId, QaStatus qaStatus,
        LocalDate startDate, LocalDate endDate) {
        checkMemberValidationById(memberId);
        return qaRepository.findQaByMemberIdAndStatusAndRegDt(memberId, qaStatus, startDate,
            endDate);
    }
}