package groom.him.domain.address.controller;

import groom.him.core.dto.Response;
import groom.him.domain.address.models.dto.request.AddAddressRequest;
import groom.him.domain.address.models.dto.response.AddressResponse;
import groom.him.domain.address.service.AddressService;
import groom.him.domain.member.models.entity.MemberEntity;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/addresses")
public class AddressController {
    private final AddressService addressService;

    @PostMapping()
    public Response<Void> addMemberAddress(@AuthenticationPrincipal MemberEntity member,
        @RequestBody AddAddressRequest addAddressRequest) {
        addressService.addMemberAddress(member, addAddressRequest);
        return new Response(HttpStatus.CREATED.value());
    }

    @GetMapping()
    public Response<List<AddressResponse>> findMemberAddressList(
        @AuthenticationPrincipal MemberEntity member) {
        return Response.success(addressService.findMemberAddressList(member.getMemberId()));
    }
}