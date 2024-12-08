package groom.him.domain.address.service;

import groom.him.domain.address.models.dto.request.AddAddressRequest;
import groom.him.domain.address.models.dto.response.AddressResponse;
import groom.him.domain.address.models.entity.AddressEntity;
import groom.him.domain.address.repository.AddressRepository;
import groom.him.domain.member.models.entity.MemberEntity;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AddressService {
    private final AddressRepository addressRepository;

    public void addMemberAddress(MemberEntity member, AddAddressRequest request) {
        AddressEntity address = new AddressEntity(member, request.name(), request.phoneNumber(),
            request.alias(), request.address(), request.addressDetail(), request.isDefault());
        addressRepository.save(address);
    }

    public List<AddressResponse> findMemberAddressList(Integer memberId) {
        return addressRepository.findAllByMemberId(memberId).stream().map((AddressResponse::of))
            .collect(Collectors.toList());
    }
}