package com.example.potatotilnewsfeed.domain.user.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.example.potatotilnewsfeed.domain.user.dto.UserRequestDto;
import com.example.potatotilnewsfeed.domain.user.dto.UserResponseDto;
import com.example.potatotilnewsfeed.domain.user.repository.TokenRepository;
import com.example.potatotilnewsfeed.domain.user.repository.UserRepository;
import com.example.potatotilnewsfeed.global.security.UserDetailsImpl;
import com.example.potatotilnewsfeed.test.UserCommonTest;
import com.example.potatotilnewsfeed.test.UserTestUtils;
import jakarta.persistence.EntityExistsException;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest implements UserCommonTest {

    @InjectMocks
    UserServiceImpl userServiceImpl;
    @Mock
    UserRepository userRepository;  // interface는 @Mock으로 안됨
    @Mock
    TokenRepository tokenRepository;
    @Mock
    PasswordEncoder passwordEncoder;

    @DisplayName("회원 가입")
    @Test
    void signup() {
        // given
        given(userRepository.findByNickname(anyString())).willReturn(
            Optional.empty());  // findByUserName 메서드가 호출되면, emty가 반환될거다. 즉, 중복된 사용자가 없다는 뜻
        given(passwordEncoder.encode(anyString())).willReturn(
            "encodedPassword");  // 비밀번호 정상적으로 인코딩 가정

        var testUser = UserTestUtils.get(TEST_USER);
        given(userRepository.save(any())).willReturn(testUser);   // 정상적으로 DB에 저장

        // when, then
        assertDoesNotThrow(
            () -> userServiceImpl.signup(
                TEST_USER_SIGNUP_REQUEST_DTO));  // 회원가입 성공시 어떠한 Exception도 던져지지 않는다.

        verify(userRepository, times(1)).findByNickname(
            anyString());  // findByUsername 메서드가 한번 호출되었는지 확인
        verify(userRepository, times(1)).save(any());

    }

    @DisplayName("회원 가입 실패 - 중복된 사용자")
    @Test
    void signup_fail_duplicateUser() {
        // given
        var testUser = UserTestUtils.get(TEST_USER);

        given(userRepository.findByNickname(anyString())).willReturn(Optional.of(testUser));  // 중복된 사용자만 있다.
        // when, then
        assertThrows(DuplicateKeyException.class,
            () -> userServiceImpl.signup(TEST_USER_SIGNUP_REQUEST_DTO));
    }

    @DisplayName("프로필 수정")
    @Test
    void updateProfile() {

        var testUser = UserTestUtils.get(TEST_USER);

        given(userRepository.findById(anyLong())).willReturn(Optional.of(testUser));

        UserDetailsImpl testUserDetails = TEST_USER_DETAILS;
        testUserDetails.getUser().setUserId(1L);

        var result =  userServiceImpl.updateUser(testUserDetails, TEST_USER_REQUEST_DTO);

        UserResponseDto userResponseDto = new UserResponseDto(testUser);
        assertThat(result.getNickname()).isEqualTo(userResponseDto.getNickname());
        assertThat(result.getIntroduce()).isEqualTo(userResponseDto.getIntroduce());
        assertThat(result.getEmail()).isEqualTo(userResponseDto.getEmail());
    }





}