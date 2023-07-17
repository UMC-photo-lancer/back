package shop.photolancer.photolancer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.photolancer.photolancer.domain.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
}
