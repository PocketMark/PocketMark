package com.example.pocketmark.dto.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.example.pocketmark.domain.user.User;
import com.example.pocketmark.security.provider.TokenBox;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserDto {


    @Getter @Builder 
    @NoArgsConstructor @AllArgsConstructor 
    public static class LeaveUserDto{
        private boolean leave;

        public static LeaveUserDto fromLeaveUserRequest(LeaveUserRequest request){
            return LeaveUserDto.builder()
                    .leave(request.isLeave())
                    .build()
                    ;
        }
    }

    @Getter @Builder 
    @NoArgsConstructor @AllArgsConstructor
    public static class LeaveUserRequest{
        @NotNull
        private boolean leave;
    }

    @Getter @Builder 
    @NoArgsConstructor @AllArgsConstructor
    public static class LeaveUserResponse{
        private boolean leave;
    }
    
    

    @Getter @Builder 
    @NoArgsConstructor @AllArgsConstructor
    public static class MyPageDto {
        private String email;
        private String nickName;
    
        public static MyPageDto fromUser(User user){
            return MyPageDto.builder()
                    .email(user.getEmail())
                    .nickName(user.getNickName())
                    .build()
                    ;
        }
    
    }


    @Getter @Builder 
    @NoArgsConstructor @AllArgsConstructor
    public static class LoginReq{
        private String email;
        private String pw;
        private String refreshToken;
    }

    @Getter @Builder 
    @NoArgsConstructor @AllArgsConstructor
    public static class LoginRes{
        private TokenBox tokenBox;
        private Long itemId;
    }


    @Getter @Builder 
    @NoArgsConstructor @AllArgsConstructor
    public static class ChangeNickNameDto{
        private String newNickName;


        public static ChangeNickNameDto fromChangeNickNameRequest(ChangeNickNameRequest request){
            return ChangeNickNameDto.builder()
                    .newNickName(request.getNewNickName())
                    .build()
                    ;
        }
    }

    @Getter @Builder 
    @NoArgsConstructor @AllArgsConstructor
    public static class ChangeNickNameRequest{

        @Size(min = 2, max = 12)
        private String newNickName;
    }

    @Getter @Builder 
    @NoArgsConstructor @AllArgsConstructor
    public static class ChangeNickNameResponse{
        private boolean isDuplicated;
        private String jwt;
    }


    @Getter @Builder 
    @NoArgsConstructor @AllArgsConstructor
    public static class ChangePwDto{
        private String nowPw;
        private String newPw;
        private String confPw;

        public static ChangePwDto fromChangePwRequest(ChangePwRequest request){
            return ChangePwDto.builder()
                    .nowPw(request.getNowPw())
                    .newPw(request.getNewPw())
                    .confPw(request.getConfPw())
                    .build()
                    ;
        }
    }

    @Getter @Builder 
    @NoArgsConstructor @AllArgsConstructor
    public static class ChangePwRequest{
        @Size(min=10)
        private String nowPw;

        @Size(min=10)
        private String newPw;

        @Size(min=10)
        private String confPw;
    }

    @Getter @Builder 
    @NoArgsConstructor @AllArgsConstructor
    public static class ChangePwResponse{
        private boolean isDuplicated;
        private String jwt;
    }


    @Getter @Builder 
    @NoArgsConstructor @AllArgsConstructor
    public static class NickNameCheckDto{
        private String nickName;

        public static NickNameCheckDto fromNickNameCheckRequest(NickNameCheckReq request){
            return NickNameCheckDto.builder()
                    .nickName(request.getNickName())
                    .build()
                    ;
        }
    }


    @Getter @Builder 
    @NoArgsConstructor @AllArgsConstructor
    public static class NickNameCheckReq{
        @Size(min = 2, max = 12)
        private String nickName;

    }


    @Getter @Builder 
    @NoArgsConstructor @AllArgsConstructor
    public static class NickNameCheckRes{
        boolean available;
    }

    
    @Getter @Builder 
    @NoArgsConstructor @AllArgsConstructor
    public static class SignUpDto{

        private String email;
        private String pw;
        private String nickName;

        public static SignUpDto fromSignUpRequest(signUpRequest request){
            return SignUpDto.builder()
                    .email(request.getEmail())
                    .pw(request.getPw())
                    .nickName(request.getNickName())
                    .build()
                    ;
        }
    }

    @Getter @Builder 
    @NoArgsConstructor @AllArgsConstructor
    public static class signUpRequest{

        @Email
        private String email;

        @Size(min=7)
        private String pw;

        @Size(min = 2, max = 12)
        private String nickName;

    }

    @Getter @Builder 
    @NoArgsConstructor @AllArgsConstructor
    public static class signUpResponse{
        private boolean isDuplicated;
        private String jwt;
    }
    
}
