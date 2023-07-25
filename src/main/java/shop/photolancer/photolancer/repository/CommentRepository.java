package shop.photolancer.photolancer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.photolancer.photolancer.domain.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
