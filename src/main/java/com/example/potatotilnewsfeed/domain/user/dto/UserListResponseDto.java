package com.example.potatotilnewsfeed.domain.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserListResponseDto {
    private final String nickname;
    private final String email;


    @Builder
    public UserListResponseDto(String nickname, String email) {
        this.nickname = nickname;
        this.email = email;
    }
}

