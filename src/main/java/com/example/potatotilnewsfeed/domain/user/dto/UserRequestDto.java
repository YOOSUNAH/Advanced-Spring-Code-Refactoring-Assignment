package com.example.potatotilnewsfeed.domain.user.dto;

import com.example.potatotilnewsfeed.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserRequestDto {

    private String nickname;
    private String introduce;

    // 기존 비밀번호를 입력해주세요
    private String password;
    // 바꾸는 비밀번호를 입력해주세요
    private String newPassword;
    // 바꾸는 비밀번호를 한번 더 입력해주세요
    private String checkPassword;


    // 생성자


}