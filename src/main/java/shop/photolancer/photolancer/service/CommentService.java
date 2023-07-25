package shop.photolancer.photolancer.service;

import shop.photolancer.photolancer.domain.Comment;
import shop.photolancer.photolancer.web.dto.CommentRequestDto;
import shop.photolancer.photolancer.web.dto.CommentResponseDto;

import java.util.List;

public interface CommentService {
    void uploadComment(CommentRequestDto.CommentUploadDto request, Long userId, Long postId);

    void uploadRecomment(CommentRequestDto.CommentUploadDto request, Long userId, Long postId, Long commentId);

    void deleteComment(Long commentId);

    void deleteRecomment(Long recommentId);

    List<CommentResponseDto.CommentsResponseDto> showComments(Long postId);
}
