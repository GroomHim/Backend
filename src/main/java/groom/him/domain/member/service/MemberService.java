package groom.him.domain.member.service;

import groom.him.core.auth.service.AuthService;
import groom.him.core.model.member.exception.MemberErrorCode;
import groom.him.core.model.member.exception.MemberException;
import groom.him.core.model.member.repository.MemberRepository;
import groom.him.domain.member.models.entity.MemberEntity;
import groom.him.domain.member.models.entity.data.Password;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final AuthService authService;
    private final MemberRepository memberRepository;

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
}