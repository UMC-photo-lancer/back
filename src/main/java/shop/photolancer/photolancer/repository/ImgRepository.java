package shop.photolancer.photolancer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.photolancer.photolancer.domain.mapping.PostImage;

public interface ImgRepository extends JpaRepository<PostImage, Long> {
}