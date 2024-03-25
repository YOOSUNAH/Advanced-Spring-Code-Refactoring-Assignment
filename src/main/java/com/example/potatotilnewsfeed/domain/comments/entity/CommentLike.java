package com.example.potatotilnewsfeed.domain.comments.entity;

import com.example.potatotilnewsfeed.domain.user.entity.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@Table(name = "commentLike")
@NoArgsConstructor
public class CommentLike { // 댓글 좋아요

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long commentLikeId;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne
  @JoinColumn(name = "comment_id")
  private Comment comment;

  public CommentLike(User user, Comment comment) {
    this.user = user;
    this.comment = comment;
  }
}
