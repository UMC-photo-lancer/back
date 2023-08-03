package shop.photolancer.photolancer.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import shop.photolancer.photolancer.domain.Follow;
import shop.photolancer.photolancer.domain.User;

@RequiredArgsConstructor
@Component
public class FollowConverter {

    public Follow toFollow(User follow, User following) {
        return Follow.builder()
                .follower(follow)
                .following(following)
                .build();
    }
}
