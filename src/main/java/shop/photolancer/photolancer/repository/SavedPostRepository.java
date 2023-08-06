package shop.photolancer.photolancer.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import shop.photolancer.photolancer.domain.Post;
import shop.photolancer.photolancer.domain.User;
import shop.photolancer.photolancer.domain.mapping.SavedPost;

public interface SavedPostRepository extends JpaRepository<SavedPost, Long> {
    SavedPost findByPostAndUser(Post post, User user);

    Page<SavedPost> findByUser(User user, Pageable pageable);
}
