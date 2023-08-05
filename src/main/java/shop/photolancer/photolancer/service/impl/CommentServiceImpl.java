package shop.photolancer.photolancer.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.photolancer.photolancer.converter.CommentConverter;
import shop.photolancer.photolancer.converter.UserConverter;
import shop.photolancer.photolancer.domain.Comment;
import shop.photolancer.photolancer.domain.Post;
import shop.photolancer.photolancer.domain.User;
import shop.photolancer.photolancer.repository.CommentRepository;
import shop.photolancer.photolancer.repository.PostRepository;
import shop.photolancer.photolancer.service.CommentService;
import shop.photolancer.photolancer.web.dto.CommentRequestDto;
import shop.photolancer.photolancer.web.dto.CommentResponseDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final PostRepository postRepository;
    public final UserServiceImpl userService;
    private final CommentConverter commentConverter;
    private final CommentRepository commentRepository;
    private final UserConverter userConverter;
    @Override
    public void uploadComment(CommentRequestDto.CommentUploadDto request, Long postId) {

        User user = userService.getCurrentUser();
        Post post = postRepository.findById(postId).orElseThrow(()
                -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));

        Comment comment= commentConverter.toComment(request, user, post);

        commentRepository.save(comment);
    }

    @Override
    public void uploadRecomment(CommentRequestDto.CommentUploadDto request, Long postId, Long commentId) {
        User user = userService.getCurrentUser();
        Post post = postRepository.findById(postId).orElseThrow(()
                -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));
        Comment recomment = commentRepository.findById(commentId).orElseThrow(()
                -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다"));
        Comment comment = commentConverter.toRecomment(request, user, post, recomment);

        commentRepository.save(comment);
    }

    @Override
    public void deleteComment(Long commentId) {
        User user = userService.getCurrentUser();
        Comment comment = commentRepository.findById(commentId).orElseThrow(()
                -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다"));
        if (user == comment.getUser()) {
            commentRepository.delete(comment);
        }
        else {
            System.out.println("유저가 아닙니다.");
        }
    }

    @Override
    public void deleteRecomment(Long recommentId) {
        User user = userService.getCurrentUser();
        Comment comment = commentRepository.findById(recommentId).orElseThrow(()
                -> new IllegalArgumentException("해당 대댓글이 존재하지 않습니다."));
        if (user == comment.getUser()) {
            commentRepository.delete(comment);
        }
        else {
            System.out.println("유저가 아닙니다.");
        }
    }

    @Override
    public List<CommentResponseDto.CommentsResponseDto> showComments(Long postId) {
        List<Comment> commentList = commentRepository.findAllByPostIdWithNullParentId(postId);

        List<CommentResponseDto.CommentsResponseDto> comments = commentList.stream().map(
                comment -> commentConverter.toComments(comment, comment.getChildComments().stream().map(
                        recomment -> commentConverter.toReComments(recomment,
                                userConverter.toUserProfile(recomment.getUser()))).collect(Collectors.toList()),
                        userConverter.toUserProfile(comment.getUser())
                        )).collect(Collectors.toList());
        return comments;
    }
}
