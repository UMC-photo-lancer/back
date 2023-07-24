package shop.photolancer.photolancer.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.photolancer.photolancer.converter.CommentConverter;
import shop.photolancer.photolancer.domain.Comment;
import shop.photolancer.photolancer.domain.Post;
import shop.photolancer.photolancer.domain.User;
import shop.photolancer.photolancer.repository.CommentRepository;
import shop.photolancer.photolancer.repository.PostRepository;
import shop.photolancer.photolancer.repository.UserRepository;
import shop.photolancer.photolancer.service.CommentService;
import shop.photolancer.photolancer.web.dto.CommentRequestDto;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentConverter commentConverter;
    private final CommentRepository commentRepository;
    @Override
    public void uploadComment(CommentRequestDto.CommentUploadDto request, Long userId,
                       Long postId) {

        User user = userRepository.findById(userId).orElseThrow(()
                -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));
        Post post = postRepository.findById(postId).orElseThrow(()
                -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));

        Comment comment= commentConverter.toComment(request, user, post);

        commentRepository.save(comment);
    }

}
