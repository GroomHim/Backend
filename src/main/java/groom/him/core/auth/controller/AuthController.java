package groom.him.core.auth.controller;

import groom.him.core.auth.dto.request.SignInRequest;
import groom.him.core.auth.dto.request.SignUpRequest;
import groom.him.core.auth.dto.request.SocialSignInRequest;
import groom.him.core.auth.dto.request.SocialSignUpRequest;
import groom.him.core.auth.dto.response.SignInResponse;
import groom.him.core.auth.service.AuthService;
import groom.him.core.models.dto.Response;
import groom.him.domain.member.exception.MemberErrorCode;
import groom.him.domain.member.exception.MemberException;
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
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/sign-in")
    public Response<SignInResponse> signin(@RequestBody SignInRequest request) throws Exception {
        SignInResponse response = authService.signIn(request.loginId(), request.password());
        return Response.success(response);
    }

    @PostMapping("/social/sign-in")
    public Response<SignInResponse> socialSignIn(@RequestBody SocialSignInRequest request) {
        SignInResponse response = authService.socialSignIn(request);
        return Response.success(response);
    }

    @PostMapping("/sign-out")
    public Response<Integer> signOut(@AuthenticationPrincipal MemberEntity member) {
        authService.signOut(member);
        return Response.success();
    }

    @PostMapping("/sign-up")
    @ResponseBody
    public Response<Integer> signUp(@RequestBody SignUpRequest request) {
        var response = authService.signUp(request);
        return Response.success(response);
    }

    @PostMapping("/social/signUp")
    @ResponseBody
    public Response<Integer> socialSignUp(@RequestBody SocialSignUpRequest request) {
        var response = authService.socialSignUp(request);
        return Response.success(response);
    }

    @GetMapping("/validate/loginId/{loginId}")
    @ResponseBody
    public Response<Integer> validateLoginId(@PathVariable String loginId) {
        if (authService.validateLoginId(loginId)) {
            return Response.success();
        } else {
            throw new MemberException(MemberErrorCode.MEMBER_NOT_VALID);
        }

    }

    @GetMapping("/validate/nickname/{nickname}")
    @ResponseBody
    public Response<Integer> validateNickname(@PathVariable String nickname) {
        if (authService.validateNickname(nickname)) {
            return Response.success();
        } else {
            throw new MemberException(MemberErrorCode.MEMBER_NOT_VALID);
        }
    }
}
