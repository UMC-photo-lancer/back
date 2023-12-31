package shop.photolancer.photolancer.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import shop.photolancer.photolancer.domain.Post;
import shop.photolancer.photolancer.domain.User;
import shop.photolancer.photolancer.web.dto.PostRequestDto;
import shop.photolancer.photolancer.web.dto.PostResponseDto;

import java.util.List;

public interface PostService {
    void upload(PostRequestDto.PostUploadDto request, List<String> imgPaths, User user);

    PostResponseDto.PostDetailDto searchById(Long postId, User user);

    void updateLike(Long postId, User user);

    void deletePost(Long postId, User user);

    void savePost(Long postId, User user);

    Page<PostResponseDto.PostListDto> savedPosts(Pageable pageable);

    Page<PostResponseDto.PostListDto> myPosts(Pageable pageable);

    Page<PostResponseDto.PostListDto> boughtPhoto(Pageable pageable);

    void sharePost(User sharedBy, List<User> shareTo, Long postId);

    void updatePost(Long id, PostRequestDto.PostUpdateDto postUpdateDto, User user);

    Post findPostById(Long postId);
}
