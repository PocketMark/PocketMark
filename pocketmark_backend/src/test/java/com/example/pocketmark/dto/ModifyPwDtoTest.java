package com.example.pocketmark.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.*;

import com.example.pocketmark.dto.user.UserDto.ChangePwDto;
import com.example.pocketmark.dto.user.UserDto.ChangePwRequest;

class ModifyPwDtoTest {

    @DisplayName("ModifyPw : Request convert dto")
    @Test
    public void fromChangePwRequest(){
        //Given
        ChangePwRequest request = ChangePwRequest.builder()
                .nowPw("1234")
                .newPw("4321")
                .confPw("4321")
                .build();

        //When
        ChangePwDto dto = ChangePwDto.fromChangePwRequest(request);

        //Then
        then(dto.getNowPw()).isEqualTo(request.getNowPw());
        then(dto.getNewPw()).isEqualTo(request.getNewPw());
        then(dto.getConfPw()).isEqualTo(request.getConfPw());

    }
}