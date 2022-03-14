package com.example.pocketmark.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.*;

import com.example.pocketmark.dto.user.UserDto.ChangeNickNameDto;
import com.example.pocketmark.dto.user.UserDto.ChangeNickNameRequest;

class ModifyNickNameDtoTest {

    @DisplayName("ModifyNickName : Request convert dto")
    @Test
    public void fromChangeNickNameRequest(){
        //Given
        ChangeNickNameRequest request =
                ChangeNickNameRequest.builder()
                        .newNickName("JyuKa")
                        .build();

        //When
        ChangeNickNameDto dto =
                ChangeNickNameDto.fromChangeNickNameRequest(request);

        //Then
        then(dto.getNewNickName()).isEqualTo(request.getNewNickName());


    }

}