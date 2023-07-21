package shop.photolancer.photolancer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import shop.photolancer.photolancer.domain.mapping.PostBookmark;

import java.util.List;

public interface PostBookmarkRepository extends JpaRepository<PostBookmark, Long> {
    @Query("SELECT pb FROM PostBookmark pb JOIN FETCH pb.bookmark b WHERE pb.post.id = :postId")
    List<PostBookmark> findByPostIdWithBookmark(Long postId);
}
