package com.example.pocketmark.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.*;

import com.example.pocketmark.dto.user.UserDto.LeaveUserDto;
import com.example.pocketmark.dto.user.UserDto.LeaveUserRequest;

class LeaveUserTest {

    @DisplayName("Leave User : request convert dto")
    @Test
    public void fromLeaveUserRequest(){
        //Given
        LeaveUserRequest request = LeaveUserRequest.builder()
                .leave(true).build();

        //When
        LeaveUserDto dto = LeaveUserDto.fromLeaveUserRequest(request);

        //Then
        then(dto.isLeave()).isEqualTo(request.isLeave());
    }

}