package com.example.pocketmark.dto.user;

import javax.validation.constraints.Email;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class AuthDto {

    @Getter @Builder 
    @NoArgsConstructor @AllArgsConstructor
    public static class EmailCheckDto{
        private String email;

        public static EmailCheckDto fromEmailCheckRequest(EmailCheckReq request){
            return EmailCheckDto.builder()
                    .email(request.getEmail())
                    .build()
                    ;
        }
    }


    @Getter @Builder 
    @NoArgsConstructor @AllArgsConstructor
    public static class EmailCheckReq{
        @Email
        private String email;

    }


    @Getter @Builder 
    @NoArgsConstructor @AllArgsConstructor
    public static class EmailCheckRes{
        boolean available;
    }

    @Getter @Builder 
    @NoArgsConstructor @AllArgsConstructor 
    public static class AuthenticationEmailDto{

        private String email;
        private String code;

        public static AuthenticationEmailDto fromSendAuthenticationEmailReq(AuthenticationEmailReq request){
            return AuthenticationEmailDto.builder()
                    .email(request.getEmail())
                    .code(request.getCode())
                    .build()
                    ;
        }

    }

    @Getter @Builder 
    @NoArgsConstructor @AllArgsConstructor 
    public static class AuthenticationEmailReq{
        private String email;
        private String code;
    }


    @Getter @Builder 
    @NoArgsConstructor @AllArgsConstructor 
    public static class AuthenticationEmailRes{

        private boolean success;

    }



    @Getter @Builder 
    @NoArgsConstructor @AllArgsConstructor
    public static class RefreshTokenDto{
        private String refreshToken;

        public static RefreshTokenDto fromRefreshTokenReq(RefreshTokenReq request){
            return RefreshTokenDto.builder()
                    .refreshToken(request.getRefreshToken())
                    .build()
                    ;
        }
    }


    @Getter @Builder 
    @NoArgsConstructor @AllArgsConstructor
    public static class RefreshTokenReq{

        private String refreshToken;

    }


    @Getter @Builder 
    @NoArgsConstructor @AllArgsConstructor
    public static class RefreshTokenRes{
        private String accessToken;
    }

    @Getter @Builder 
    @NoArgsConstructor @AllArgsConstructor
    public static class SendAuthenticationEmailDto{

        private String email;

        public static SendAuthenticationEmailDto fromSendAuthenticationEmailReq(SendAuthenticationEmailReq request){
            return SendAuthenticationEmailDto.builder()
                    .email(request.getEmail())
                    .build()
                    ;
        }

    }

    @Getter @Builder 
    @NoArgsConstructor @AllArgsConstructor
    public static class SendAuthenticationEmailReq{
        private String email;
    }


}
