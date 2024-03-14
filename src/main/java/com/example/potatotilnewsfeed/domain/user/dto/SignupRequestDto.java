package com.example.potatotilnewsfeed.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SignupRequestDto {

    @NotBlank(message = "아이디는 필수로 입력해야 합니다.")
    @Size(max = 16, message = "아이디는 16자 이하이어야 합니다.")
    private String nickname;

    @NotBlank(message = "비밀번호는 필수로 입력해야 합니다.")
    @Size(max = 16, message = "비밀번호는 16자 이하이어야 합니다.")
    private String password;

    @NotBlank(message = "이메일은 필수로 입력해야 합니다.")
    @Email(message = "이메일 형식에 맞지 않습니다.")
    private String email;
}