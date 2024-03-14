package com.example.potatotilnewsfeed.domain.user.aop.controller;

// import 주의

import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
        doNothing().when(userServiceImpl).signup(TEST_USER_REQUEST_DTO);

        // when
        var action = mockMvc.perform(post("/v1/users/signup")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(TEST_USER_REQUEST_DTO)));

        // then
        action.andExpect(status().isOk());
        verify(userServiceImpl, times(1)).signup(any());
    }
}