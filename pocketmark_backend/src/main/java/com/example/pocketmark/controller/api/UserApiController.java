package com.example.pocketmark.controller.api;

import com.example.pocketmark.dto.*;
import com.example.pocketmark.dto.common.ApiDataResponse;
import com.example.pocketmark.dto.user.AuthDto.EmailCheckDto;
import com.example.pocketmark.dto.user.AuthDto.EmailCheckReq;
import com.example.pocketmark.dto.user.AuthDto.EmailCheckRes;
import com.example.pocketmark.dto.user.UserDto.ChangeNickNameDto;
import com.example.pocketmark.dto.user.UserDto.ChangeNickNameRequest;
import com.example.pocketmark.dto.user.UserDto.ChangeNickNameResponse;
import com.example.pocketmark.dto.user.UserDto.ChangePwDto;
import com.example.pocketmark.dto.user.UserDto.ChangePwRequest;
import com.example.pocketmark.dto.user.UserDto.ChangePwResponse;
import com.example.pocketmark.dto.user.UserDto.LeaveUserDto;
import com.example.pocketmark.dto.user.UserDto.LeaveUserRequest;
import com.example.pocketmark.dto.user.UserDto.LeaveUserResponse;
import com.example.pocketmark.dto.user.UserDto.MyPageDto;
import com.example.pocketmark.dto.user.UserDto.NickNameCheckDto;
import com.example.pocketmark.dto.user.UserDto.NickNameCheckReq;
import com.example.pocketmark.dto.user.UserDto.NickNameCheckRes;
import com.example.pocketmark.dto.user.UserDto.SignUpDto;
import com.example.pocketmark.dto.user.UserDto.signUpRequest;
import com.example.pocketmark.dto.user.UserDto.signUpResponse;
import com.example.pocketmark.security.provider.UserPrincipal;
import com.example.pocketmark.service.LoginService;
import com.example.pocketmark.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.Api;

import javax.validation.Valid;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Api(tags={"User API (회원가입/개인페이지/로그아웃)"})
public class UserApiController {

    private final LoginService loginService;
    private final UserService userService;

    private Long getUserId(){
        UserPrincipal userPrincipal = (UserPrincipal)SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        return Long.parseLong(userPrincipal.getUsername());
    }


    @PostMapping("/sign-up")
    public ApiDataResponse<signUpResponse> signUp(
            @Valid @RequestBody signUpRequest request
    ){

        loginService.signUp(SignUpDto.fromSignUpRequest(request));
        return ApiDataResponse.empty();

    }

    @GetMapping("/myPage")
    public ApiDataResponse<MyPageDto> myPage(){
        return ApiDataResponse.of(
                MyPageDto.fromUser(
                        userService.selectUserByUserId(getUserId())
                )
        );

    }

    @PutMapping("/changePassword")
    public ApiDataResponse<ChangePwResponse> changePassword(
            @Valid @RequestBody ChangePwRequest request
    ){

        userService.modifyPassword(ChangePwDto.fromChangePwRequest(request),getUserId());
        return ApiDataResponse.empty();
    }


    @PutMapping("/changeNickName")
    public ApiDataResponse<ChangeNickNameResponse> changeNickName(
            @Valid @RequestBody ChangeNickNameRequest request
    ){
        userService.modifyNickName(ChangeNickNameDto.fromChangeNickNameRequest(request),getUserId());
        return ApiDataResponse.empty();
    }

    @PutMapping("/userLeave")
    public ApiDataResponse<LeaveUserResponse> leaveUser(
            @Valid @RequestBody LeaveUserRequest request
    ){
        userService.deleteUser(LeaveUserDto.fromLeaveUserRequest(request),getUserId());
        return ApiDataResponse.empty();
    }

    @PostMapping("/email-check")
    public ApiDataResponse<EmailCheckRes> emailCheck(
            @Valid @RequestBody EmailCheckReq request
    ){
       boolean result = userService
               .checkAvailableEmail(EmailCheckDto.fromEmailCheckRequest(request));


        return ApiDataResponse.of(
                        EmailCheckRes
                        .builder()
                        .available(result)
                        .build());
    }

    @PostMapping("/alias-check")
    public ApiDataResponse<NickNameCheckRes> nickNameCheck(
            @Valid @RequestBody NickNameCheckReq request
    ){
        boolean result = userService
                .checkAvailableNickName(NickNameCheckDto.fromNickNameCheckRequest(request));


        return ApiDataResponse.of(
                        NickNameCheckRes
                        .builder()
                        .available(result)
                        .build()
        );
    }



}
