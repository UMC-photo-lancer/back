package shop.photolancer.photolancer.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import shop.photolancer.photolancer.web.dto.FollowingResponseDto;

import java.util.List;

public interface FollowService {
    void requestFollow(String followingName, Long userId);

    Page<FollowingResponseDto.FollowingUserPostsDto> followingUsersPosts(Pageable pageable);
}
