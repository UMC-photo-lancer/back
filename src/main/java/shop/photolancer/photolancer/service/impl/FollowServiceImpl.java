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
import shop.photolancer.photolancer.domain.mapping.UserPhoto;
import shop.photolancer.photolancer.repository.FollowRepository;
import shop.photolancer.photolancer.repository.PostRepository;
import shop.photolancer.photolancer.repository.UserPhotoRepository;
import shop.photolancer.photolancer.repository.UserRepository;
import shop.photolancer.photolancer.service.FollowService;
import shop.photolancer.photolancer.web.dto.FollowingResponseDto;
import shop.photolancer.photolancer.web.dto.PostResponseDto;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowServiceImpl implements FollowService {

    private final UserRepository userRepository;
    private final FollowRepository followRepository;
    private final FollowConverter followConverter;
    private final UserServiceImpl userService;
    private final PostRepository postRepository;
    private final PostConverter postConverter;
    private final UserPhotoRepository userPhotoRepository;
    @Override
    public void requestFollow(String followingName, Long userId) {
        User user = userRepository.findById(userId).orElseThrow();
        User followingUser = userService.findUserByUserName(followingName);
        Follow follow = followRepository.findByFollowerAndFollowing(user, followingUser);
            // user following 수 추가
            if (follow == null) {
                Follow following = followConverter.toFollow(user, followingUser);
                followRepository.save(following);
            }
            else {
                followRepository.delete(follow);
            }
        }

    @Override
    public Page<FollowingResponseDto.FollowingUserPostsDto> followingUsersPosts(Pageable pageable) {
        User user = userService.findUserByUserName("조승연");
        Page<Follow> followingList = followRepository.findByFollower(user, pageable);

        List<FollowingResponseDto.FollowingUserPostsDto> followingUsersPosts =
                followingList.stream().map(following -> {
                    List<Post> postList = postRepository.findByUserOrderByCreatedAtDesc(following.getFollowing(), PageRequest.of(0, 3));

                    List<PostResponseDto.PostListDto> posts =
                            postList.stream().map(post -> {
                                UserPhoto userPhoto = userPhotoRepository.findByUserAndPost(user, post);
                                if (userPhoto == null) {
                                    PostResponseDto.PostListDto postListDto = postConverter.toPostList(post, false);
                                    return postListDto;
                                }
                                PostResponseDto.PostListDto postListDto = postConverter.toPostList(post, true);
                                return postListDto;
                            }).toList();
                    return followConverter.toFollowingUsersPosts(following, posts);
                }).toList();
            return new PageImpl<>(followingUsersPosts, pageable, followingList.getTotalElements());
    }
}
