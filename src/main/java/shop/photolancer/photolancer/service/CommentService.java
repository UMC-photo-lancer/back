package shop.photolancer.photolancer.service;

import shop.photolancer.photolancer.web.dto.CommentRequestDto;

public interface CommentService {
    void uploadComment(CommentRequestDto.CommentUploadDto request, Long userId, Long postId);

    void uploadRecomment(CommentRequestDto.CommentUploadDto request, Long userId, Long postId, Long commentId);

    void deleteComment(Long commentId);
}
