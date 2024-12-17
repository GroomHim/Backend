package groom.him.domain.agreement.service;

import groom.him.core.model.member.exception.MemberErrorCode;
import groom.him.core.model.member.exception.MemberException;
import groom.him.core.model.member.repository.MemberRepository;
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

    public AgreementEntity createAgreement(CreateAgreementRequest request){
        MemberEntity member = memberRepository.findById(request.memberId()).orElseThrow(() -> new MemberException(
            MemberErrorCode.MEMBER_NOT_EXIST));
        AgreementEntity agreement = request.from(request);
        agreement.modifyMember(member);
        return agreementRepository.save(agreement);
    }
}
