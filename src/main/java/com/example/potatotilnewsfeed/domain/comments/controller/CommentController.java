package com.example.potatotilnewsfeed.domain.comments.controller;

import com.example.potatotilnewsfeed.domain.comments.dto.CommentRequestDto;
import com.example.potatotilnewsfeed.domain.comments.dto.CommentResponseDto;
import com.example.potatotilnewsfeed.domain.comments.service.CommentService;
import com.example.potatotilnewsfeed.domain.til.service.TilService;
import com.example.potatotilnewsfeed.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class CommentController { // 어떤 형태로 값을 주고 받을 것인가? json(숫자, 문자)

  private final CommentService commentService;
  private final TilService tilService;


  @PostMapping("/tils/{tilId}/comments")
  public ResponseEntity<CommentResponseDto> createComment(@PathVariable Long tilId,
      @RequestBody CommentRequestDto requestDto,
      @AuthenticationPrincipal UserDetailsImpl userDetails) {
    // 댓글 작성, 응답코드 : 200
    CommentResponseDto createDto = commentService.createComment(tilId, requestDto,
        userDetails.getUser());

    return ResponseEntity.ok().body(createDto);
  }

  @PutMapping("/tils/{tilId}/comments/{commentId}")
  public ResponseEntity<CommentResponseDto> updateComment(@PathVariable Long tilId,
      @PathVariable Long commentId, @RequestBody CommentRequestDto requestDto,
      @AuthenticationPrincipal UserDetailsImpl userDetails) throws BadRequestException {
    // 댓글 수정, 응답코드 : 200
    CommentResponseDto updateDto = commentService.updateComment(tilId, commentId, requestDto,
        userDetails.getUser());

    return ResponseEntity.ok().body(updateDto);

  }

  @DeleteMapping("/tils/{tilId}/comments/{commentId}")
  public ResponseEntity<Void> deleteComment(@PathVariable Long tilId,
      @PathVariable Long commentId) {
    // 댓글 삭제, 응답코드 : 204
    commentService.deleteComment(tilId, commentId);

    return ResponseEntity.noContent().build();
  }

}
