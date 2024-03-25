package com.example.potatotilnewsfeed.domain.comments.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequestDto {

  @NotBlank(message = "댓글 내용은 비워 둘 수 없습니다.")
  @Size(max = 64, message = "댓글 내용은 최대 64글자까지 가능합니다.")
  private String content;
  private Long tilId;
  private Long commentId;
  private Long userId;

}
