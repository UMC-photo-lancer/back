package shop.photolancer.photolancer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.photolancer.photolancer.domain.Bookmark;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    Bookmark findByName(String name);

}
