package shop.photolancer.photolancer.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import shop.photolancer.photolancer.converter.FollowConverter;
import shop.photolancer.photolancer.converter.PostConverter;
import shop.photolancer.photolancer.domain.Follow;
import shop.photolancer.photolancer.domain.Post;
import shop.photolancer.photolancer.domain.User;
import shop.photolancer.photolancer.repository.FollowRepository;
import shop.photolancer.photolancer.repository.PostRepository;
import shop.photolancer.photolancer.service.FollowService;
import shop.photolancer.photolancer.web.dto.FollowingResponseDto;
import shop.photolancer.photolancer.web.dto.PostResponseDto;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowServiceImpl implements FollowService {

    private final FollowRepository followRepository;
    private final FollowConverter followConverter;
    private final UserServiceImpl userService;
    private final PostRepository postRepository;
    private final PostConverter postConverter;
    private final SavedPostServiceImpl savedPostService;
    private final PostLikeServiceImpl postLikeService;
    private final UserPhotoServiceImpl userPhotoService;

    @Override
    public void requestFollow(Long followingUserId, Long userId) {
        User user = userService.findUserById(userId);
        User followingUser = userService.findUserById(followingUserId);
        Follow follow = followRepository.findByFollowerAndFollowing(user, followingUser);

            if (follow == null) {
                Follow following = followConverter.toFollow(user, followingUser);
                user.setNum_following(user.getNum_following()+1);
                user.setNum_follower(user.getNum_follower()+1);
                followRepository.save(following);
            }
            else {
                user.setNum_following(user.getNum_following()-1);
                user.setNum_follower(user.getNum_follower()-1);
                followRepository.delete(follow);
            }
        }

    @Override
    public Page<FollowingResponseDto.FollowingUserPostsDto> followingUsersPosts(Pageable pageable, User user) {
        Page<Follow> followingList = followRepository.findByFollower(user, pageable);

        List<FollowingResponseDto.FollowingUserPostsDto> followingUsersPosts =
                followingList.stream().map(following -> {
                    List<Post> postList = postRepository.findByUserOrderByCreatedAtDesc(following.getFollowing(), PageRequest.of(0, 3));

                    List<PostResponseDto.PostListDto> posts =
                            postList.stream().map(post -> {
                                Boolean isUserPhoto = userPhotoService.isUserPhoto(post, user);
                                Boolean savedPost = savedPostService.isSavedPost(post, user);
                                Boolean likeStatus = postLikeService.likeStatus(post, user);
                                PostResponseDto.PostListDto postListDto = postConverter.toPostList(post, isUserPhoto, savedPost, likeStatus);
                                return postListDto;
                            }).toList();
                    return followConverter.toFollowingUsersPosts(following, posts);
                }).toList();
            return new PageImpl<>(followingUsersPosts, pageable, followingList.getTotalElements());
    }
}
