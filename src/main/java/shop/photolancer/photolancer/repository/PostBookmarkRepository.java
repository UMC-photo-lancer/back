package shop.photolancer.photolancer.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import shop.photolancer.photolancer.domain.Bookmark;
import shop.photolancer.photolancer.domain.Post;
import shop.photolancer.photolancer.domain.mapping.PostBookmark;

import java.util.List;

public interface PostBookmarkRepository extends JpaRepository<PostBookmark, Long> {
    @Query("SELECT pb FROM PostBookmark pb JOIN FETCH pb.bookmark b WHERE pb.post.id = :postId")
    List<PostBookmark> findByPostIdWithBookmark(Long postId);

    @Query("SELECT pb.post FROM PostBookmark pb JOIN pb.bookmark b ON pb.bookmark.id = b.id WHERE b.name = :postBookmarkName")
    Page<Post> findByPostBookmarkName(Pageable pageable, @Param("postBookmarkName") String postBookmarkName);

    List<PostBookmark> findByPost(Post post);

    PostBookmark findByBookmarkAndPost(Bookmark bookmark, Post post);

    @Query("SELECT pb FROM PostBookmark pb JOIN pb.bookmark b ON pb.bookmark.id = b.id WHERE b.name = :postBookmarkName")
    PostBookmark findByPostBookmarkName(@Param("postBookmarkName") String postBookmarkName);
}
