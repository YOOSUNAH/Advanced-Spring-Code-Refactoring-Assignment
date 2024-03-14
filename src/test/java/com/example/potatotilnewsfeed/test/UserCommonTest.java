package com.example.potatotilnewsfeed.test;

import com.example.potatotilnewsfeed.domain.user.dto.SignupRequestDto;
import com.example.potatotilnewsfeed.domain.user.dto.UserRequestDto;
import com.example.potatotilnewsfeed.domain.user.dto.UserResponseDto;
import com.example.potatotilnewsfeed.domain.user.entity.User;
import com.example.potatotilnewsfeed.domain.user.entity.UserRoleEnum;

public interface UserCommonTest {

    String ANOTHER_PREFIX = "another-";
    UserRoleEnum USER_ROLE = UserRoleEnum.USER;
    String TEST_USER_NAME = "username12";
    String TEST_WRONG_USER_NAME = "Invalid username";
    String TEST_USER_PASSWORD = "password12^^";
    String TEST_WRONG_USER_PASSWORD = "Invalid password";
    String TEST_NEW_PASSWORD = "newPassword12!@";
    String TEST_CHECK_PASSWORD = "newPassword12!@";
    String TEST_USER_EMAIL = "sonny12@gmail.com";
    String TEST_USER_NICKNAME = "nickname";
    String TEST_USER_INTRODUCE = "introduce";
    String TEST_WRONG_USER_NICKNAME = "12345";
    String TEST_USER_INFO = "info";
    String TEST_USER_NAME_MESSAGE = "아이디는 최소 4자 이상, 10자 이하로 알파벳 소문자와 숫자로 구성되어야 합니다.";
    String TEST_USER_PASSWORD_MESSAGE = "비밀번호는 최소 8자 이상, 15자 이하로 알파벳과 특수문자, 숫자로 구성되어야 합니다.";
    String TEST_USER_NICKNAME_MESSAGE = "닉네임은 2~10자로 구성되어야 하며, 숫자로만 구성될 수 없습니다.";

    String TOKEN = "test-token";
    String INVALID_TOKEN = "invalid-token";

    User TEST_USER = User.builder()
        .nickname(TEST_USER_NAME)
        .password(TEST_USER_PASSWORD)
        .nickname(TEST_USER_NICKNAME)
        .build();

    User TEST_DUPLCATE_USER = User.builder()
        .nickname(ANOTHER_PREFIX + TEST_USER_NAME)
        .password(ANOTHER_PREFIX + TEST_USER_PASSWORD)
        .nickname(ANOTHER_PREFIX + TEST_USER_NICKNAME)
        .build();
    SignupRequestDto TEST_USER_SIGNUP_REQUEST_DTO = SignupRequestDto.builder()
        .nickname(TEST_USER_NAME)
        .password(TEST_USER_PASSWORD)
        .email(TEST_USER_EMAIL)
        .build();

    UserRequestDto TEST_USER_REQUEST_DTO = UserRequestDto.builder()
        .nickname(TEST_USER_NAME)
        .introduce(TEST_USER_INTRODUCE)
        .build();


    UserResponseDto TEST_USER_RESPONSE_DTO = UserResponseDto.builder()
        .user(TEST_USER)
        .build();



}