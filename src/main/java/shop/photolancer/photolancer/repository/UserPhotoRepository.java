package shop.photolancer.photolancer.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import shop.photolancer.photolancer.domain.Post;
import shop.photolancer.photolancer.domain.User;
import shop.photolancer.photolancer.domain.mapping.UserPhoto;

public interface UserPhotoRepository extends JpaRepository<UserPhoto, Long> {
    UserPhoto findByUserAndPost(User user, Post post);

    Page<UserPhoto> findByUser(User user, Pageable pageable);
}
