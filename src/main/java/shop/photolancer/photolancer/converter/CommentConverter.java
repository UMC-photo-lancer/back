package shop.photolancer.photolancer.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import shop.photolancer.photolancer.domain.Comment;
import shop.photolancer.photolancer.domain.Post;
import shop.photolancer.photolancer.domain.User;
import shop.photolancer.photolancer.web.dto.CommentRequestDto;
import shop.photolancer.photolancer.web.dto.CommentResponseDto;
import shop.photolancer.photolancer.web.dto.UserResponseDto;

import java.util.List;

@RequiredArgsConstructor
@Component
public class CommentConverter {
    public Comment toComment(CommentRequestDto.CommentUploadDto request,
                             User user, Post post) {
        return Comment.builder()
                .post(post)
                .content(request.getContent())
                .user(user)
                .build();
    }
    public Comment toRecomment(CommentRequestDto.CommentUploadDto request,
                             User user, Post post, Comment comment) {
        return Comment.builder()
                .post(post)
                .content(request.getContent())
                .user(user)
                .parentComment(comment)
                .build();
    }

    //전체 댓글 조회
    public CommentResponseDto.CommentsResponseDto toComments(Comment comment,
                                                             List<CommentResponseDto.RecommentsResponseDto> recomments,
                                                             UserResponseDto.PostUserDto user) {
        return CommentResponseDto.CommentsResponseDto.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .recomments(recomments)
                .user(user)
                .build();
    }
    //대댓글 조회
    public CommentResponseDto.RecommentsResponseDto toReComments(Comment comment, UserResponseDto.PostUserDto user) {
        return CommentResponseDto.RecommentsResponseDto.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .user(user)
                .build();
    }
}
