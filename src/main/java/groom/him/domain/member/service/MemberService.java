package groom.him.domain.member.service;

import groom.him.core.auth.service.AuthService;
import groom.him.domain.member.exception.MemberErrorCode;
import groom.him.domain.member.exception.MemberException;
import groom.him.domain.member.models.dto.request.CancelMemberRequest;
import groom.him.domain.member.models.dto.request.ModifyMyInfoRequest;
import groom.him.domain.member.models.dto.response.MemberResponse;
import groom.him.domain.member.models.entity.MemberCancelLogEntity;
import groom.him.domain.member.models.entity.MemberEntity;
import groom.him.domain.member.models.entity.data.Password;
import groom.him.domain.member.repository.MemberCancelLogRepository;
import groom.him.domain.member.repository.MemberRepository;
import groom.him.domain.qa.models.dto.response.QaResponse;
import groom.him.domain.qa.models.enums.QaStatus;
import groom.him.domain.qa.repository.QaRepository;
import groom.him.domain.skinType.models.entity.SkinTypeEntity;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final AuthService authService;
    private final MemberRepository memberRepository;
    private final QaRepository qaRepository;
    private final MemberCancelLogRepository memberCancelLogRepository;

    @Transactional
    public MemberResponse findMyInfo(Integer memberId) {
        MemberEntity member = findByMemberIdAndIsCancelFalse(memberId);
        return MemberResponse.of(member);
    }

    @Transactional
    public MemberResponse modifyMyInfo(Integer memberId, ModifyMyInfoRequest request) {
        authService.validateNickname(request.nickname());

        MemberEntity member = findByMemberIdAndIsCancelFalse(memberId);
        member.changeNicknameAndEmailAndBirth(request.nickname(), request.email(), request.birth());
        return MemberResponse.of(member);
    }

    @Transactional
    public void softDelete(Integer memberId, CancelMemberRequest request) {
        MemberEntity member = findByMemberIdAndIsCancelFalse(memberId);
        MemberCancelLogEntity memberCancelLog = MemberCancelLogEntity.of(member, request.reason());
        memberCancelLogRepository.save(memberCancelLog);
        member.softDelete();
    }

    @Transactional
    public void modifyPassword(Integer memberId, String newPassword) {
        MemberEntity member = findByMemberIdAndIsCancelFalse(memberId);
        Password password = authService.encryptPassword(newPassword);
        member.changePassword(password);
    }

    @Transactional
    public void modifySkinType(Integer memberId, SkinTypeEntity skinType) {
        MemberEntity member = findByMemberIdAndIsCancelFalse(memberId);
        member.changeSkinType(skinType);
    }

    public MemberEntity findByMemberIdAndIsCancelFalse(Integer memberId) {
        return memberRepository.findByMemberIdAndIsCancelFalse(memberId)
            .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_EXIST));
    }

    public void validatePassword(Integer memberId, String password) {
        MemberEntity member = findByMemberIdAndIsCancelFalse(memberId);
        String encryptPassword = authService.hashing(password, member.getSalt());

        if (!member.getPassword().equals(encryptPassword)) {
            throw new MemberException(MemberErrorCode.MEMBER_INVALID_PASSWORD);
        }
    }

    public List<QaResponse> findMemberQaList(Integer memberId, QaStatus qaStatus,
        LocalDate startDate, LocalDate endDate) {
        return qaRepository.findQaByMemberIdAndStatusAndRegDt(memberId, qaStatus, startDate,
            endDate);
    }
}