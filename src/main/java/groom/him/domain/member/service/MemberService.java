package groom.him.domain.member.service;

import groom.him.common.models.entity.SkinTypeEntity;
import groom.him.core.auth.service.AuthService;
import groom.him.core.model.member.exception.MemberErrorCode;
import groom.him.core.model.member.exception.MemberException;
import groom.him.core.model.member.repository.MemberRepository;
import groom.him.domain.member.models.dto.request.ModifyMyInfoRequest;
import groom.him.domain.member.models.dto.response.MemberResponse;
import groom.him.domain.member.models.entity.MemberEntity;
import groom.him.domain.member.models.entity.data.Password;
import groom.him.domain.qa.models.dto.response.QaResponse;
import groom.him.domain.qa.models.enums.QaStatus;
import groom.him.domain.qa.repository.QaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final AuthService authService;
    private final MemberRepository memberRepository;
    private final QaRepository qaRepository;

    @Transactional
    public MemberResponse findMyInfo(Integer memberId) {
        MemberEntity member = findById(memberId);
        return MemberResponse.of(member);
    }

    @Transactional
    public MemberResponse modifyMyInfo(Integer memberId, ModifyMyInfoRequest request) {
        authService.validateNickname(request.nickname());

        MemberEntity member = findById(memberId);
        member.changeNicknameAndEmail(request.nickname(), request.email());
        return MemberResponse.of(member);
    }

    @Transactional
    public void modifyPassword(Integer memberId, String newPassword) {
        MemberEntity member = findById(memberId);
        Password password = authService.encryptPassword(newPassword);
        member.changePassword(password);
    }

    @Transactional
    public MemberResponse modifySkinType(Integer memberId, SkinTypeEntity skinType) {
        MemberEntity member = findById(memberId);
        member.changeSkinType(skinType);
        return MemberResponse.of(member);
    }

    public MemberEntity findById(Integer memberId) {
        return memberRepository.findById(memberId)
            .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_EXIST));
    }

    public void validatePassword(Integer memberId, String password) {
        MemberEntity member = findById(memberId);
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