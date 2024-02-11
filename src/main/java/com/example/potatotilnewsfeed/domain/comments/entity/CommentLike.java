package com.example.potatotilnewsfeed.domain.comments.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Getter
@Table(name = "commentLike")
public class CommentLike { // 댓글 좋아요

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long commentLikeId;

  @JoinColumn(nullable = false, unique = true)
  private Long userId;

  @JoinColumn(nullable = false, unique = true)
  private Long commentId;
}
