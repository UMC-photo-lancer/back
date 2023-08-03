package shop.photolancer.photolancer.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.photolancer.photolancer.converter.FollowConverter;
import shop.photolancer.photolancer.domain.Follow;
import shop.photolancer.photolancer.domain.User;
import shop.photolancer.photolancer.repository.FollowRepository;
import shop.photolancer.photolancer.repository.UserRepository;
import shop.photolancer.photolancer.service.FollowService;

@Service
@RequiredArgsConstructor
public class FollowServiceImpl implements FollowService {

    private final UserRepository userRepository;
    private final FollowRepository followRepository;
    private final FollowConverter followConverter;
    private final UserServiceImpl userService;
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
    }
