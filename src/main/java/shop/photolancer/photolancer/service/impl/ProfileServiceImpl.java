package shop.photolancer.photolancer.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import shop.photolancer.photolancer.converter.PostConverter;
import shop.photolancer.photolancer.domain.Post;
import shop.photolancer.photolancer.domain.User;
import shop.photolancer.photolancer.repository.PostRepository;
import shop.photolancer.photolancer.web.dto.PostResponseDto;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl {

    private final SavedPostServiceImpl savedPostService;
    private final PostLikeServiceImpl postLikeService;
    private final UserPhotoServiceImpl userPhotoService;
    private final PostConverter postConverter;
    private final PostRepository postRepository;

    public Page<PostResponseDto.PostListDto> userPosts(Pageable pageable, User currentUser, User user) {
        Page<Post> myPostList = postRepository.findByUser(user, pageable);

        Page<PostResponseDto.PostListDto> myPostPage = myPostList.map(
                myPost -> {
                    Boolean savedPost = savedPostService.isSavedPost(myPost, currentUser);
                    Boolean likeStatus = postLikeService.likeStatus(myPost, currentUser);
                    Boolean isUserPhoto = userPhotoService.isUserPhoto(myPost, currentUser);
                    PostResponseDto.PostListDto postListDto = postConverter.toPostList(myPost, isUserPhoto, savedPost, likeStatus);

                    return postListDto;
                });
        return myPostPage;
    }
}
