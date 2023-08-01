package shop.photolancer.photolancer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.photolancer.photolancer.domain.mapping.PostImage;

import java.util.List;

public interface PostImgRepository extends JpaRepository<PostImage, Long> {
    List<PostImage> findByPostId(Long postId);

    void deleteByPostId(Long postId);
}
