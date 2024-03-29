package com.example.pocketmark.dto;

import lombok.*;

import javax.validation.constraints.Size;

public class RefreshToken {

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @ToString
    public static class RefreshTokenDto{
        private String refreshToken;

        public static RefreshTokenDto fromRefreshTokenReq(RefreshTokenReq request){
            return RefreshTokenDto.builder()
                    .refreshToken(request.getRefreshToken())
                    .build()
                    ;
        }
    }


    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @ToString
    public static class RefreshTokenReq{

        private String refreshToken;

    }


    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @ToString
    public static class RefreshTokenRes{
        private String accessToken;
    }
}
