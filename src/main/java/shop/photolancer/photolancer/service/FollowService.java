package shop.photolancer.photolancer.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import shop.photolancer.photolancer.domain.User;
import shop.photolancer.photolancer.web.dto.FollowingResponseDto;
import shop.photolancer.photolancer.web.dto.UserResponseDto;

import java.util.List;

public interface FollowService {
    void requestFollow(Long followingUserId, Long userId);

    Page<FollowingResponseDto.FollowingUserPostsDto> followingUsersPosts(Pageable pageable, User user);

    List<UserResponseDto.PostUserDto> followingUsers(User user);
}
