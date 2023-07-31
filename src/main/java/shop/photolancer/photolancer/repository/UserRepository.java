package shop.photolancer.photolancer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import shop.photolancer.photolancer.domain.User;
import shop.photolancer.photolancer.domain.enums.SocialType;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByName(String name);
    boolean existsByUserId(String user_id);
    boolean existsByEmail(String email);
    boolean existsByNickname(String nickname);
    Optional<User> findByNameAndEmail(String name, String email);

    User findByUserId(String userId);

    Optional<User> findByName(String userName);

    User findByEmail(String email);

    Optional<User> findBySocialTypeAndSocialId(SocialType socialType, String id);

    @Modifying
    @Query("UPDATE User u SET u.password = :password WHERE u.id = :id")
    void updatePassword(@Param("id") Long id, @Param("password") String password);
}