package groom.him.domain.address.service;

import groom.him.core.exception.CommonErrorCode;
import groom.him.domain.address.exception.AddressException;
import groom.him.domain.address.models.dto.request.AddAddressRequest;
import groom.him.domain.address.models.dto.request.ModifyAddressRequest;
import groom.him.domain.address.models.dto.response.AddressResponse;
import groom.him.domain.address.models.entity.AddressEntity;
import groom.him.domain.address.models.enums.AddressErrorCode;
import groom.him.domain.address.repository.AddressRepository;
import groom.him.domain.member.models.entity.MemberEntity;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AddressService {
    private final AddressRepository addressRepository;

    private static final Integer MAX_ADDRESS_CNT = 10;

    @Transactional
    public void addMemberAddress(MemberEntity member, AddAddressRequest request) {
        if (addressRepository.findAllByMemberId(member.getMemberId()).size() >= MAX_ADDRESS_CNT) {
            throw new AddressException(AddressErrorCode.MAX_ADDRESS_LIMIT_EXCEEDED);
        }
        if (request.isDefault()) {
            setPreviousDefaultAddressFalse(member.getMemberId());
        }

        AddressEntity address = new AddressEntity(member, request.name(), request.phoneNumber(),
            request.alias(), request.address(), request.addressDetail(), request.isDefault());
        addressRepository.save(address);
    }

    public List<AddressResponse> findMemberAddressList(Integer memberId) {
        return addressRepository.findAllByMemberId(memberId).stream().map((AddressResponse::of))
            .collect(Collectors.toList());
    }

    @Transactional
    public void modifyAddress(Integer memberId, Integer addressId, ModifyAddressRequest request) {
        AddressEntity addressEntity = addressRepository.findById(addressId)
            .orElseThrow(() -> new AddressException(AddressErrorCode.ADDRESS_NOT_EXIST));

        if (request.isDefault()) {
            setPreviousDefaultAddressFalse(memberId);
        }

        if (!addressEntity.getMember().getMemberId().equals(memberId)) {
            throw new AddressException(CommonErrorCode.UNAUTHORIZED);
        }
        addressEntity.modifyAddress(request);
    }

    private void setPreviousDefaultAddressFalse(Integer memberId) {
        addressRepository.findByMemberIdAndIsDefaultTrue(memberId)
            .ifPresent(entity -> entity.changeIsDefault(false));
    }
}