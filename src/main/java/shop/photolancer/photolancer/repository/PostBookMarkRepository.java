package shop.photolancer.photolancer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.photolancer.photolancer.domain.mapping.PostBookmark;

public interface PostBookMarkRepository extends JpaRepository<PostBookmark, Long> {

}
