package groom.him.domain.member.service;

import groom.him.core.dto.Response;
import groom.him.core.model.member.exception.MemberErrorCode;
import groom.him.core.model.member.exception.MemberException;
import groom.him.core.model.member.repository.MemberRepository;
import groom.him.domain.member.models.dto.request.ModifyMyInfoRequest;
import groom.him.domain.member.models.dto.response.MemberResponse;
import groom.him.domain.product.models.dto.response.ProductBriefResponse;
import groom.him.domain.product.repository.ProductRepository;
import groom.him.domain.qa.models.dto.response.QaResponse;
import groom.him.domain.qa.models.enums.QaStatus;
import groom.him.domain.qa.repository.QaRepository;
import jakarta.transaction.Transactional;

import java.time.LocalDate;

import groom.him.core.auth.service.AuthService;
import groom.him.domain.member.models.entity.MemberEntity;
import groom.him.domain.member.models.entity.data.Password;

import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final AuthService authService;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;
    private final QaRepository qaRepository;

    public MemberResponse findMyInfo(Integer memberId) {
        MemberEntity member = getMemberById(memberId);
        return MemberResponse.from(member);
    }

    @Transactional
    public MemberResponse modifyMyInfo(Integer memberId, ModifyMyInfoRequest request) {
        authService.validateNickname(request.nickname());

        MemberEntity member = getMemberById(memberId);
        member.changeNicknameAndEmail(request.nickname(), request.email());
        return MemberResponse.from(member);
    }

    @Transactional
    public void modifyPassword(Integer memberId, String newPassword) {
        MemberEntity member = getMemberById(memberId);
        Password password = authService.encryptPassword(newPassword);
        member.changePassword(password);
    }

    private MemberEntity getMemberById(Integer memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_EXIST));
    }

    public void validatePassword(Integer memberId, String password) {
        MemberEntity member = getMemberById(memberId);
        String encryptPassword = authService.hashing(password, member.getSalt());

        if (!member.getPassword().equals(encryptPassword)) {
            throw new MemberException(MemberErrorCode.MEMBER_INVALID_PASSWORD);
        }
    }

    public List<ProductBriefResponse> findMemberWishList(Integer memberId, Boolean isSkinType) {
        return productRepository.findMemberWishProductBriefBySkinType(memberId, isSkinType).stream()
                .map(ProductBriefResponse::of).collect(Collectors.toList());
    }

    public List<QaResponse> findMemberQaList(Integer memberId, QaStatus qaStatus,
                                             LocalDate startDate, LocalDate endDate) {
        return qaRepository.findQaByMemberIdAndStatusAndRegDt(memberId, qaStatus, startDate,
                endDate);
    }
}