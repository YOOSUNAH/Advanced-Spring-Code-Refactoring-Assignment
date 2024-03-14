package com.example.potatotilnewsfeed.domain.user.service;

import com.example.potatotilnewsfeed.domain.user.dto.SignupRequestDto;
import com.example.potatotilnewsfeed.domain.user.dto.UserListResponseDto;
import com.example.potatotilnewsfeed.domain.user.dto.UserRequestDto;
import com.example.potatotilnewsfeed.domain.user.dto.UserResponseDto;
import com.example.potatotilnewsfeed.domain.user.entity.Token;
import com.example.potatotilnewsfeed.domain.user.entity.User;
import com.example.potatotilnewsfeed.domain.user.repository.TokenRepository;
import com.example.potatotilnewsfeed.domain.user.repository.UserRepository;
import com.example.potatotilnewsfeed.global.exception.DuplicatePasswordException;
import com.example.potatotilnewsfeed.global.exception.NotFoundException;
import com.example.potatotilnewsfeed.global.security.UserDetailsImpl;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void signup(SignupRequestDto requestDto) {
        String nickname = requestDto.getNickname();
        String password = passwordEncoder.encode(requestDto.getPassword());
        String email = requestDto.getEmail();

        validateUserDuplicate(userRepository.findByNickname(nickname));

        User user = new User(nickname, password, email);
        userRepository.save(user);
    }

    private void validateUserDuplicate(Optional<User> checkUsername) {
        if (checkUsername.isPresent()) {
            throw new DuplicateKeyException("중복된 사용자가 존재합니다.");
        }
    }

    @Override
    @Transactional
    public void logout(String accessToken) {
        Token token = tokenRepository.findById(accessToken).orElseThrow(() ->
            new NoSuchElementException("로그인되지 않은 토큰입니다."));
        token.setIsExpired(true);
    }

    public UserResponseDto getUser(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = validateUser(userDetails);
        return new UserResponseDto(user);
    }

    @Override
    @Transactional
    public UserResponseDto updateUser(@AuthenticationPrincipal UserDetailsImpl userDetails,
        UserRequestDto userRequestDto) {
        User user = validateUser(userDetails);
        // 변경
        user.setNickname(userRequestDto.getNickname());
        user.setIntroduce(userRequestDto.getIntroduce());
        return new UserResponseDto(user);
    }

    @Transactional
    public UserResponseDto updatePassword(UserDetailsImpl userDetails,
        UserRequestDto userRequestDto) {
        User user = validateUser(userDetails);
        //  입력받은 값과 기존 비밀번호 확인
        validatePassword(userRequestDto.getPassword(), user.getPassword());
        // 바꾸는 비밀번호
        String newPassword = userRequestDto.getNewPassword();
        // 바꾸는 비밀번호 확인
        validateNewPassword(newPassword, userRequestDto.getCheckPassword());

        // 바꾸는 비밀번호 인코딩해서 저장
        newPassword = passwordEncoder.encode(userRequestDto.getNewPassword());
        user.setPassword(newPassword);

        return new UserResponseDto(user);
    }

    @Transactional
    public void deleteUser(UserDetailsImpl userDetails, UserRequestDto userRequestDto) {
        User user = validateUser(userDetails);
        String password = userRequestDto.getPassword();
        String checkPassword = user.getPassword();

        // raw  , check (인코딩 된것)
        validatePassword(password, checkPassword);
        userRepository.delete(user);
    }

    public void validatePassword(String rawPassword, String encodedPassword) {
        if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
            throw new BadCredentialsException("패스워드를 잘못 입력하셨습니다.");
        }
    }

    private void validateNewPassword(String newPassword, String checkPassword) {
        if (!newPassword.equals(checkPassword)) {
            throw new DuplicatePasswordException("기존 비밀번호와 동일합니다.");
        }
    }

    private User validateUser(UserDetailsImpl userDetails) {
        // 토큰으로 id 가져오기
        Long userId = userDetails.getUser().getUserId();
        // DB에 접근
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new NotFoundException("선택한 유저가 존재하지 않습니다."));

        return user;

    }

    public List<UserListResponseDto> getUsersWithEmail(String email) {
        List<User> users = userRepository.findByEmailEndPoint(email);
        return users.stream()
            .map(user -> UserListResponseDto.builder()
                .nickname(user.getNickname())
                .email(user.getEmail())
                .build())
            .collect(Collectors.toList());
    }
}
