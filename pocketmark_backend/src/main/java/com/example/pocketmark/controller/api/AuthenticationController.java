package com.example.pocketmark.controller.api;

import com.example.pocketmark.domain.auth.RefreshToken;
import com.example.pocketmark.dto.common.ApiDataResponse;
import com.example.pocketmark.dto.user.AuthDto.AuthenticationEmailDto;
import com.example.pocketmark.dto.user.AuthDto.AuthenticationEmailReq;
import com.example.pocketmark.dto.user.AuthDto.AuthenticationEmailRes;
import com.example.pocketmark.dto.user.AuthDto.RefreshTokenDto;
import com.example.pocketmark.dto.user.AuthDto.RefreshTokenReq;
import com.example.pocketmark.dto.user.AuthDto.RefreshTokenRes;
import com.example.pocketmark.dto.user.AuthDto.SendAuthenticationEmailDto;
import com.example.pocketmark.dto.user.AuthDto.SendAuthenticationEmailReq;
import com.example.pocketmark.dto.user.UserDto.LoginReq;
import com.example.pocketmark.dto.user.UserDto.LoginRes;
import com.example.pocketmark.security.provider.JwtUtil;
import com.example.pocketmark.security.provider.TokenBox;
import com.example.pocketmark.service.AuthenticationService;
import com.example.pocketmark.service.DataService;
import com.example.pocketmark.service.EmailService;
import com.example.pocketmark.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;

import org.hibernate.engine.jdbc.connections.internal.DatasourceConnectionProviderImpl;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Api(tags={"인증관련 API (로그인, 토큰재발급, 이메일인증)"})
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final RefreshTokenService refreshTokenService;
    private final DataService dataService;
    private final EmailService emailService;

    @PostMapping("/login")
    public ApiDataResponse<LoginRes> login(
            @RequestBody LoginReq req
    ){
        TokenBox tokenBox = authenticationService.authenticate(req);
        
        return ApiDataResponse.of(LoginRes.builder()
                .tokenBox(tokenBox)
                .itemId(dataService.getLastItemId(JwtUtil.getUserId(tokenBox.getAccessToken())))
                .build());

    }

    @PostMapping("/refresh-token")
    public ApiDataResponse<RefreshTokenRes> refreshJwtToken(
            @RequestBody RefreshTokenReq req
    ){
        RefreshTokenRes res = refreshTokenService.refreshAccessToken(
                RefreshTokenDto.fromRefreshTokenReq(req)
        );

        return ApiDataResponse.of(res);
    }


    @PostMapping("/send-authentication-email")
    public ApiDataResponse<ApiDataResponse.GeneralResponse> sendAuthenticationEmail(
            @RequestBody SendAuthenticationEmailReq req
    ){

        emailService.sendSignUpAuthenticationMail(SendAuthenticationEmailDto.fromSendAuthenticationEmailReq(req));
        return ApiDataResponse.success();
    }


    @PostMapping("/authentication-email")
    public ApiDataResponse<AuthenticationEmailRes> authenticationEmail(
            @RequestBody AuthenticationEmailReq req
    ){

        boolean authenticationResult = emailService.authenticateEmail(
                        AuthenticationEmailDto
                        .fromSendAuthenticationEmailReq(req)
        );

        return ApiDataResponse.of(
                        AuthenticationEmailRes
                        .builder()
                        .success(authenticationResult)
                        .build()
        );
    }
}
