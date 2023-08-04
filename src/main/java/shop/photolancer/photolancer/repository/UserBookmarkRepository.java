package shop.photolancer.photolancer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.photolancer.photolancer.domain.Bookmark;
import shop.photolancer.photolancer.domain.User;
import shop.photolancer.photolancer.domain.mapping.UserBookmark;

import java.util.List;

public interface UserBookmarkRepository extends JpaRepository<UserBookmark, Long> {
    List<UserBookmark> findByUserId(Long id);

    List<UserBookmark> findByUserAndBookmark(User user, Bookmark bookmark);

}
