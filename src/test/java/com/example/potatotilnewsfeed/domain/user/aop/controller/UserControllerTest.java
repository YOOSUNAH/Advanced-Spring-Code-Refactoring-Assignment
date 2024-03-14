package com.example.potatotilnewsfeed.domain.user.aop.controller;

// import 주의

import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.potatotilnewsfeed.domain.user.service.UserServiceImpl;
import com.example.potatotilnewsfeed.global.security.UserDetailsImpl;
import com.example.potatotilnewsfeed.test.UserCommonTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(UserController.class)
class UserControllerTest implements UserCommonTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserServiceImpl userServiceImpl;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

        UserDetailsImpl testUserDetails = new UserDetailsImpl(TEST_USER);

        SecurityContextHolder.getContext()
            .setAuthentication(new UsernamePasswordAuthenticationToken(
                testUserDetails, testUserDetails.getPassword(), testUserDetails.getAuthorities()));
    }

    @DisplayName("회원가입 요청")
    @Test
    void signup() throws Exception {
        // given
        doNothing().when(userServiceImpl).signup(TEST_USER_SIGNUP_REQUEST_DTO);

        // when
        var action = mockMvc.perform(post("/v1/users/signup")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(TEST_USER_SIGNUP_REQUEST_DTO)));

        // then
        action.andExpect(status().isOk());
        verify(userServiceImpl, times(1)).signup(any());
    }

    @DisplayName("로그아웃 요청")
    @Test
    void logout() throws Exception {
        // when
        var action = mockMvc.perform(patch("/v1/users/logout")
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", TOKEN));

        // then
        action.andExpect(status().isOk());
    }

    @DisplayName("프로필 조회 요청")
    @Test
    void getUser() throws Exception {
        // when
        var action = mockMvc.perform(get("/v1/users")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON));
        // then
        action.andExpect(status().isOk());
    }

    @DisplayName("프로필 수정 요청")
    @Test
    void updateUser() throws Exception {
        // when
        var action = mockMvc.perform(put("/v1/users")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(TEST_USER_REQUEST_DTO)));

        // then
        action.andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value("프로필 수정 성공"));
    }


    @DisplayName("프로필 비밀번호 수정 요청")
    @Test
    void updateUserPassword() throws Exception {
        // when
        var action = mockMvc.perform(put("/v1/users/password")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(TEST_USER_REQUEST_DTO)));

        // then
        action.andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value("프로필 비밀번호 수정 성공"));
    }

    @DisplayName("유저 삭제 요청")
    @Test
    void deleteUser() throws Exception {
        // when
        var action = mockMvc.perform(delete("/v1/users")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(TEST_USER_REQUEST_DTO)));

        // then
        action.andExpect(status().isOk());
    }

    @DisplayName("프로필 email을 기준으로 조회 요청")
    @Test
    void getUserWithEmail() throws Exception {
        // when
        var action = mockMvc.perform(get("/v2/users")
            .contentType(MediaType.APPLICATION_JSON)
            .param("email", TEST_USER_EMAIL)
            .accept(MediaType.APPLICATION_JSON));
        // then
        action.andExpect(status().isOk());
    }
}

