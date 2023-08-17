package shop.photolancer.photolancer.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import shop.photolancer.photolancer.domain.Follow;
import shop.photolancer.photolancer.domain.Notification;
import shop.photolancer.photolancer.domain.User;
import shop.photolancer.photolancer.domain.enums.NotificationType;
import shop.photolancer.photolancer.web.dto.FollowingResponseDto;
import shop.photolancer.photolancer.web.dto.PostResponseDto;

import java.util.List;

@RequiredArgsConstructor
@Component
public class FollowConverter {

    public Follow toFollow(User follow, User following) {
        return Follow.builder()
                .follower(follow)
                .following(following)
                .build();
    }
    public FollowingResponseDto.FollowingUserPostsDto toFollowingUsersPosts(Follow following, List<PostResponseDto.PostListDto> postList) {
        return FollowingResponseDto.FollowingUserPostsDto.builder()
                .id(following.getFollowing().getId())
                .nickname(following.getFollowing().getNickname())
                .profileUrl(following.getFollowing().getProfileUrl())
                .level(following.getFollowing().getLevel())
                .postList(postList)
                .build();
    }

    public Notification toFollowNotification(User follower, User following){
        return Notification.builder()
                .message("Lv. " + follower.getLevel() + " " + follower.getNickname() + "님이 " + following.getNickname() + "님을 팔로우하였습니다.")
                .type(NotificationType.FOLLOW)
                .user(following)
                .build();
    }
}
