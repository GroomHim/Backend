package groom.him.domain.agreement.service;

import groom.him.domain.member.exception.MemberErrorCode;
import groom.him.domain.member.exception.MemberException;
import groom.him.domain.member.repository.MemberRepository;
import groom.him.domain.agreement.models.dto.CreateAgreementRequest;
import groom.him.domain.agreement.models.entity.AgreementEntity;
import groom.him.domain.agreement.repository.AgreementRepository;
import groom.him.domain.member.models.entity.MemberEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AgreementService {
    private final AgreementRepository agreementRepository;
    private final MemberRepository memberRepository;

    public AgreementEntity addAgreement(CreateAgreementRequest request){
        MemberEntity member = memberRepository.findById(request.memberId()).orElseThrow(() -> new MemberException(
            MemberErrorCode.MEMBER_NOT_EXIST));
        AgreementEntity agreement = CreateAgreementRequest.from(request);
        agreement.modifyMember(member);
        return agreementRepository.save(agreement);
    }
}
