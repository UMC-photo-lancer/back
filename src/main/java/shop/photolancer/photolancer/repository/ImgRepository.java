package shop.photolancer.photolancer.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import shop.photolancer.photolancer.domain.mapping.PostImage;

import java.util.List;

public interface ImgRepository extends JpaRepository<PostImage, Long> {
    List<PostImage> findByPostId(Long postId);

    @Query("SELECT pi FROM PostImage pi WHERE pi.id IN (SELECT MIN(pii.id) FROM PostImage pii GROUP BY pii.post.id)")
    Page<PostImage> findExplore(Pageable pageable);
}
