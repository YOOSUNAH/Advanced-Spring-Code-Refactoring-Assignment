package com.example.potatotilnewsfeed.domain.user.aop.controller;

import com.example.potatotilnewsfeed.domain.user.aop.annotation.Timer;
import com.example.potatotilnewsfeed.domain.user.dto.SignupRequestDto;
import com.example.potatotilnewsfeed.domain.user.dto.UserListResponseDto;
import com.example.potatotilnewsfeed.domain.user.dto.UserRequestDto;
import com.example.potatotilnewsfeed.domain.user.dto.UserResponseDto;
import com.example.potatotilnewsfeed.domain.user.service.UserServiceImpl;
import com.example.potatotilnewsfeed.global.dto.ResponseDto;
import com.example.potatotilnewsfeed.global.security.UserDetailsImpl;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@EnableMethodSecurity(securedEnabled = true)
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userServiceImpl;
    public static final String SIGN_UP_API = "회원 가입 API";
    public static final String SIGN_UP_SUCCESS = "회원 가입 성공";
    public static final String LOG_OUT_API = "로그아웃 API";
    public static final String LOG_OUT_SUCCESS = "로그아웃 성공";

    @PostMapping("/v1/users/signup")
    public ResponseEntity<ResponseDto<Void>> signup(
        @RequestBody @Valid SignupRequestDto requestDto) {
        log.info(SIGN_UP_API);

        userServiceImpl.signup(requestDto);

        return ResponseEntity.ok()
            .body(ResponseDto.<Void>builder()
                .message(SIGN_UP_SUCCESS)
                .build());
    }

    @PatchMapping("/v1/users/logout")
    public ResponseEntity<ResponseDto<Void>> logout(
        @RequestHeader(value = "Authorization") String accessToken) {
        log.info(LOG_OUT_API);

        userServiceImpl.logout(accessToken);

        return ResponseEntity.ok()
            .body(ResponseDto.<Void>builder()
                .message(LOG_OUT_SUCCESS)
                .build());
    }

    @Timer
    @GetMapping("/v1/users")
    public ResponseEntity<ResponseDto<UserResponseDto>> getUser(
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        UserResponseDto userResponseDto = userServiceImpl.getUser(userDetails);
        return ResponseEntity.ok()
            .body(ResponseDto.<UserResponseDto>builder()
                .message("프로필 조회 성공")
                .data(userResponseDto)
                .build());
    }

    @PutMapping("/v1/users")
    public ResponseEntity<ResponseDto<UserResponseDto>> updateUser(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @RequestBody UserRequestDto userRequestDto) {
        UserResponseDto userResponseDto = userServiceImpl.updateUser(userDetails,
            userRequestDto);
        return ResponseEntity.ok()
            .body(ResponseDto.<UserResponseDto>builder()
                .message("프로필 수정 성공")
                .data(userResponseDto)
                .build());
    }

    @PutMapping("/v1/users/password")
    public ResponseEntity<ResponseDto<UserResponseDto>> updatePassword(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @RequestBody UserRequestDto userRequestDto) {

        UserResponseDto userResponseDto = userServiceImpl.updatePassword(userDetails,
            userRequestDto);
        return ResponseEntity.ok()
            .body(ResponseDto.<UserResponseDto>builder()
                .message("프로필 비밀번호 수정 성공")
                .data(userResponseDto)
                .build());
    }


    @DeleteMapping("/v1/users")
    public void deleteUser(@AuthenticationPrincipal UserDetailsImpl userDetails,
        @RequestBody UserRequestDto userRequestDto) {

        userServiceImpl.deleteUser(userDetails, userRequestDto);
        // 삭제 서비스 로직 : 소요시간 2초로 설정
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("v2/users")
    public ResponseEntity<List<UserListResponseDto>> getUserWithEmail(
        @RequestParam("email") String email) {
        return ResponseEntity.ok(userServiceImpl.getUsersWithEmail(email));
    }
}

