package shop.photolancer.photolancer.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import shop.photolancer.photolancer.domain.Post;
import shop.photolancer.photolancer.domain.User;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE Post p SET p.likeCount = :likeCount WHERE p.id = :id")
    void updateLikeCount(Long id, Integer likeCount);

    Page<Post> findAll(Pageable request);


    List<Post> findByUser(User user);

    Page<Post> findByUser(User user, Pageable pageable);

    @Query("SELECT p FROM Post p WHERE p.user = :user ORDER BY p.createdAt DESC")
    List<Post> findByUserOrderByCreatedAtDesc(@Param("user") User user, Pageable pageable);

    @Transactional
    @Modifying
    @Query("UPDATE Post p SET p.content = :content, p.isSale = :isSale, p.point = :point WHERE p.id = :postId")
    void updatePost(@Param("postId") Long postId, @Param("content") String content, @Param("isSale") Boolean isSale,
                        @Param("point") Integer point);
}
