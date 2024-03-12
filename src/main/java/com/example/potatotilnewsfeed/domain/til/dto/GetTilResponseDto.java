package com.example.potatotilnewsfeed.domain.til.dto;

import com.example.potatotilnewsfeed.domain.til.entity.Til;
import com.example.potatotilnewsfeed.domain.user.entity.User;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GetTilResponseDto {

    private String nickname;
    private Long tilId;
    private String content;
    private LocalDateTime createAt;
    private int likes;

    @Builder
    public GetTilResponseDto(Til til, User user, int likes) {
        nickname = user.getNickname();
        tilId = til.getId();
        content = til.getContent();
        createAt = til.getCreatedAt();
        this.likes = likes;
    }
}
