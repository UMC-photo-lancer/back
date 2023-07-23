package shop.photolancer.photolancer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.photolancer.photolancer.domain.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByName(String name);
    boolean existsByUserId(String user_id);
    boolean existsByEmail(String email);
    boolean existsByNickname(String nickname);
    Optional<User> findByNameAndEmail(String name, String email);
}