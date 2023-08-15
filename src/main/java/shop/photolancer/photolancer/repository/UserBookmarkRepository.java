package shop.photolancer.photolancer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.photolancer.photolancer.domain.Bookmark;
import shop.photolancer.photolancer.domain.User;
import shop.photolancer.photolancer.domain.mapping.UserBookmark;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public interface UserBookmarkRepository extends JpaRepository<UserBookmark, Long> {
    List<UserBookmark> findByUserId(Long id);

    List<UserBookmark> findByUserAndBookmark(User user, Bookmark bookmark);

    Optional<Object> findByIdAndUser(Long id, User user);
}
