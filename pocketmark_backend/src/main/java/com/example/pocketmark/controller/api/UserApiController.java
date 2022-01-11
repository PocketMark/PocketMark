package com.example.pocketmark.controller.api;

import com.example.pocketmark.dto.*;
import com.example.pocketmark.dto.LoginDto.LoginReq;
import com.example.pocketmark.dto.SignUpUserDto.SignUpEmailCheckReq;
import com.example.pocketmark.dto.SignUpUserDto.SignUpNickNameCheckReq;
import com.example.pocketmark.dto.common.ApiDataResponse;
import com.example.pocketmark.dto.common.ApiDataResponse.GeneralResponse;
import com.example.pocketmark.repository.UserRepository;
import com.example.pocketmark.service.LoginService;
import com.example.pocketmark.service.UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UserApiController {

    private final LoginService loginService;
    private final UserService userService;

    @PostMapping("/sign-up")
    public ApiDataResponse<SignUpUserDto.signUpResponse> signUp(
            @Valid @RequestBody SignUpUserDto.signUpRequest request
    ){
        loginService.signUp(SignUpUserDto.SignUpDto.fromSignUpRequest(request));
        return ApiDataResponse.empty();
    }

    @GetMapping("/myPage")
    public ApiDataResponse<MyPageDto> myPage(Principal principal){
        System.out.println(principal.getName());
        return ApiDataResponse.of(MyPageDto.fromUser(userService.selectUserByToken(1L)));

    }

    @PutMapping("/changePassword")
    public ApiDataResponse<ModifyPwDto.ChangePwResponse> changePassword(
            @Valid @RequestBody ModifyPwDto.ChangePwRequest request,
            @AuthenticationPrincipal Long email
    ){

        userService.modifyPassword(ModifyPwDto.ChangePwDto.fromChangePwRequest(request),email);
        return ApiDataResponse.empty();
    }


    @PutMapping("/changeNickName")
    public ApiDataResponse<ModifyNickNameDto.ChangeNickNameResponse> changeNickName(
            @Valid @RequestBody ModifyNickNameDto.ChangeNickNameRequest request,
            @AuthenticationPrincipal Long email
    ){
        userService.modifyNickName(ModifyNickNameDto.ChangeNickNameDto.fromChangeNickNameRequest(request),email);
        return ApiDataResponse.empty();
    }

    @PutMapping("/userLeave")
    public ApiDataResponse<LeaveUser.LeaveUserResponse> leaveUser(
            @Valid @RequestBody LeaveUser.LeaveUserRequest request,
            @AuthenticationPrincipal Long email
    ){
        userService.deleteUser(LeaveUser.LeaveUserDto.fromLeaveUserRequest(request),email);
        return ApiDataResponse.empty();
    }


    @PostMapping("/login")
    public ApiDataResponse<LoginDto.LoginRes> login(
        @RequestBody LoginReq req,
        HttpServletResponse res
    ){

        return ApiDataResponse.of(LoginDto.LoginRes.builder()
                .tokenBox(loginService.login(req))
                .build());

    }



    /* 
    프론트 요구사항 : 
    - 이메일 중복체크
    - 닉네임 중복체크
    */

    //임시로 만든 영역입니다. 
    @Autowired UserRepository userRepository;
    @PostMapping("/email-check")
    public ApiDataResponse<GeneralResponse> emailCheck(
        @RequestBody SignUpEmailCheckReq req
    ){
        if(userRepository.findByEmail(req.getEmail()).isPresent()){
            return ApiDataResponse.success();
        }
        return ApiDataResponse.failed();
    }
    
    //임시로 만든 영역입니다. 
    @PostMapping("/alias-check")
    public ApiDataResponse<GeneralResponse> nickNameCheck(
        @RequestBody SignUpNickNameCheckReq req
    ){
        if(userRepository.findByNickName(req.getNickName()).isPresent()){
            return ApiDataResponse.success();
        }
        return ApiDataResponse.failed();
    }




}
