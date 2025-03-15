//package groom.v1.address.controller;
//
//import groom.him.core.models.dto.Response;
//import groom.v1.address.models.dto.request.AddAddressRequest;
//import groom.v1.address.models.dto.request.DeleteAddressListRequest;
//import groom.v1.address.models.dto.request.ModifyAddressRequest;
//import groom.v1.address.models.dto.response.AddressResponse;
//import groom.v1.address.service.AddressService;
//import groom.him.domain.member.models.entity.MemberEntity;
//import java.util.List;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PatchMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/v1/addresses")
//public class AddressController {
//    private final AddressService addressService;
//
//    @PostMapping()
//    public Response<Void> addMemberAddress(@AuthenticationPrincipal MemberEntity member,
//        @RequestBody AddAddressRequest addAddressRequest) {
//        addressService.addMemberAddress(member, addAddressRequest);
//        return new Response<>(HttpStatus.CREATED.value());
//    }
//
//    @GetMapping()
//    public Response<List<AddressResponse>> findMemberAddressList(
//        @AuthenticationPrincipal MemberEntity member) {
//        return Response.success(addressService.findMemberAddressList(member.getMemberId()));
//    }
//
//    @PatchMapping("{address-id}")
//    public Response<Void> modifyAddress(@AuthenticationPrincipal MemberEntity member,
//        @PathVariable("address-id") Integer addressId, @RequestBody ModifyAddressRequest request) {
//        addressService.modifyAddress(member.getMemberId(), addressId, request);
//        return new Response<>(HttpStatus.OK.value());
//    }
//
//    @DeleteMapping()
//    public Response<Void> deleteAddressList(@AuthenticationPrincipal MemberEntity member,
//        @RequestBody DeleteAddressListRequest request) {
//        addressService.deleteAddressList(member.getMemberId(), request.addressIdList());
//        return new Response<>(HttpStatus.NO_CONTENT.value());
//    }
//}