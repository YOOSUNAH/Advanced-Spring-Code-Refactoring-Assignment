package com.example.potatotilnewsfeed.domain.comments.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentLikeResponseDto {

  private String message;
  private Long tilId;
  private Long userId;
  private Long commentId;
}
