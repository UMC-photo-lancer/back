package shop.photolancer.photolancer.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import shop.photolancer.photolancer.domain.Comment;
import shop.photolancer.photolancer.domain.Post;
import shop.photolancer.photolancer.domain.User;
import shop.photolancer.photolancer.web.dto.CommentRequestDto;

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
}
