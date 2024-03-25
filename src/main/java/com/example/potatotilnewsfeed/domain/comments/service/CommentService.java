package com.example.potatotilnewsfeed.domain.comments.service;

import com.example.potatotilnewsfeed.domain.comments.dto.CommentRequestDto;
import com.example.potatotilnewsfeed.domain.comments.dto.CommentResponseDto;
import com.example.potatotilnewsfeed.domain.comments.entity.Comment;
import com.example.potatotilnewsfeed.domain.comments.repository.CommentRepository;
import com.example.potatotilnewsfeed.domain.til.entity.Til;
import com.example.potatotilnewsfeed.domain.til.repository.TilRepository;
import com.example.potatotilnewsfeed.domain.user.entity.User;
import com.example.potatotilnewsfeed.global.exception.NotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

  private final CommentRepository commentRepository;
  private final TilRepository tilRepository;


  // 댓글 작성
  public CommentResponseDto createComment(Long tilId, @Valid CommentRequestDto requestDto, User user)
      throws BadRequestException {
    Til til = tilRepository.findById(tilId).orElseThrow(
        () -> new NotFoundException("해당 TIL을 찾을 수 없습니다.")
    );

    Comment register = new Comment(til, user, requestDto.getContent());
    if (requestDto.getContent().length() > 64) {
        throw new BadRequestException("64글자를 초과했습니다.");
    }

    commentRepository.save(register);
    return new CommentResponseDto("댓글이 등록되었습니다.", register.getTil().getId(),
        register.getCommentId(),
        register.getUser().getUserId(), register.getContent());
  }

  // 댓글 수정
  public CommentResponseDto updateComment(Long tilId, Long commentId,
    @Valid  CommentRequestDto requestDto, User user) throws BadRequestException {
    Comment comment = commentRepository.findById(commentId).orElseThrow(
        () -> new NotFoundException("해당 commentId를 찾을 수 없습니다.")
    );

    if (!comment.getTil().getId().equals(tilId)) {
      throw new IllegalArgumentException("TIL 정보가 일치하지 않습니다.");
    }

    if (requestDto.getContent().length() > 64) {
      throw new BadRequestException("64글자를 초과했습니다.");
    }

    comment.setContent(requestDto.getContent());

    return new CommentResponseDto("댓글이 수정되었습니다.", tilId,
        commentId, user.getUserId(), comment.getContent());
  }

  // 댓글 삭제
  public void deleteComment(Long tilId, Long commentId) {
    commentRepository.deleteById(commentId);
  }

}
