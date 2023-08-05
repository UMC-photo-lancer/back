package shop.photolancer.photolancer.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import shop.photolancer.photolancer.domain.Follow;
import shop.photolancer.photolancer.domain.User;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    Follow findByFollowerAndFollowing(User follower, User following);

    Page<Follow> findByFollower(User user, Pageable pageable);
}
