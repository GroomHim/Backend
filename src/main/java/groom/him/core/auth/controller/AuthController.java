package groom.him.core.auth.controller;

import groom.him.core.auth.dto.request.SignUpRequest;
import groom.him.core.auth.service.AuthService;
import groom.him.core.dto.Response;
import groom.him.domain.member.models.entity.MemberEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    //    @PostMapping
//    public LogInResponse logIn(){
//
//    }
//
//    @PostMapping
//    public Response logOut(){
//    }

    @PostMapping("/sign-up")
    @ResponseBody
    public Response signUp(@RequestBody SignUpRequest request) throws Exception {
        MemberEntity member = authService.signUp(request);
        return Response.success(member);
    }
}
