package shop.photolancer.photolancer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import shop.photolancer.photolancer.domain.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("SELECT e FROM Comment e WHERE e.parentComment IS NULL AND e.post.id = :postId")
    List<Comment> findAllByPostIdWithNullParentId(Long postId);
}
