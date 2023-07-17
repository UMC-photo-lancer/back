package shop.photolancer.photolancer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.photolancer.photolancer.domain.mapping.UserPhoto;

public interface UserPhotoRepository extends JpaRepository<UserPhoto, Long> {
}
