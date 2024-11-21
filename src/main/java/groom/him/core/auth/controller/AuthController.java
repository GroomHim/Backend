package groom.him.core.auth.controller;

import groom.him.core.auth.dto.request.SignInRequest;
import groom.him.core.auth.dto.request.SignUpRequest;
import groom.him.core.auth.dto.response.SignInResponse;
import groom.him.core.auth.service.AuthService;
import groom.him.core.dto.Response;
import groom.him.core.model.member.exception.MemberErrorCode;
import groom.him.core.model.member.exception.MemberException;
import groom.him.domain.member.models.entity.MemberEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/sign-in")
    public Response<SignInResponse> signIn(@RequestBody SignInRequest request) throws Exception {
        SignInResponse response = authService.signIn(request.loginId(), request.password());
        return Response.success(response);
    }

    @PostMapping("/sign-out")
    public Response<Integer> signOut(@AuthenticationPrincipal MemberEntity member){
        authService.signOut(member);
        return Response.success();
    }

    @PostMapping("/sign-up")
    @ResponseBody
    public Response<Integer> signUp(@RequestBody SignUpRequest request) throws Exception {
        authService.signUp(request);
        return Response.success();
    }

    @GetMapping("/validate/login-id/{loginId}")
    @ResponseBody
    public Response<Integer> validateLoginId(@PathVariable String loginId) {
        if (authService.validateLoginId(loginId)){
            return Response.success();
        }
        else throw new MemberException(MemberErrorCode.MEMBER_NOT_VALID);
    }

    @GetMapping("/validate/nickname/{nickname}")
    @ResponseBody
    public Response<Integer> validateNickname(@PathVariable String nickname){
        if (authService.validateNickname(nickname)){
            return Response.success();
        }
        else throw new MemberException(MemberErrorCode.MEMBER_NOT_VALID);
    }
}
