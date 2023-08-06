package shop.photolancer.photolancer.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import shop.photolancer.photolancer.domain.User;
import shop.photolancer.photolancer.web.dto.FollowingResponseDto;

public interface FollowService {
    void requestFollow(Long followingUserId, Long userId);

    Page<FollowingResponseDto.FollowingUserPostsDto> followingUsersPosts(Pageable pageable, User user);
}
