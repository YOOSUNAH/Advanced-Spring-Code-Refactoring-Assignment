package com.example.potatotilnewsfeed.global.jwt;

import static com.example.potatotilnewsfeed.global.jwt.JwtUtil.BEARER_PREFIX;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

import io.jsonwebtoken.Claims;
import com.example.potatotilnewsfeed.test.UserCommonTest;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test") // value어노테이션 정보를 가져올 수 있게 하고, test가 붙은 설정을 가지고 옴 "application-test.properties"
class JwtUtilTest implements UserCommonTest {

    @Autowired
    JwtUtil jwtUtil;

    @Mock
    private HttpServletRequest request;

    @BeforeEach
        // 각각의 테스트 코드를 실행하기 전에 실행하겠다.
    void setUp() {
        jwtUtil.init();  // secretkey 값 설정
    }

    @DisplayName("토큰 생성")
    @Test
    void createToken() {
        // when
        String token = jwtUtil.createToken(TEST_USER_NAME, USER_ROLE);

        // then
        assertNotNull(token);
    }

    @DisplayName("토큰 추출")
    @Test
    void resolveToken() {
        // given
        var token = TOKEN;
        var bearerToken = BEARER_PREFIX + token;


        // when
        given(request.getHeader(JwtUtil.AUTHORIZATION_HEADER)).willReturn(bearerToken);
        var resolvedToken = jwtUtil.getTokenWithoutBearer(bearerToken);

        // then
        assertEquals(token, resolvedToken);
    }

    @DisplayName("토큰 검증")
    @Nested
    class validateToken {

        @DisplayName("토큰 검증 성공")
        @Test
        void validateToken_success() {
            // given
            String token = jwtUtil.createToken(TEST_USER_NAME, USER_ROLE).substring(7);

            // when
            boolean isValid = jwtUtil.validateToken(token);

            // then
            assertTrue(isValid);
        }

        @DisplayName("토큰 검증 실패 - 유효하지 않은 토큰")
        @Test
        void validateToken_fail() {
            // given
            String invalidToken = INVALID_TOKEN;

            // when
            boolean isValid = jwtUtil.validateToken(invalidToken);  // isValid가 false 일테니 assertFalse로 확인한다.

            // then
            assertFalse(isValid);
        }
    }

    @DisplayName("토큰에서 UserInfo 조회")
    @Test
    void getUserInfoFromToken() {
        // given
        String token = jwtUtil.createToken(TEST_USER_NAME, USER_ROLE).substring(7);

        // when
        Claims claims = jwtUtil.getUserInfoFromToken(token);
        // then
        assertNotNull(claims);
        assertEquals(TEST_USER_NAME, claims.getSubject());
    }

}