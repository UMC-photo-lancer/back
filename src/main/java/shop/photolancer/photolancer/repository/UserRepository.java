package shop.photolancer.photolancer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.photolancer.photolancer.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByName(String name);
    boolean existsByUser_id(String user_id);
}