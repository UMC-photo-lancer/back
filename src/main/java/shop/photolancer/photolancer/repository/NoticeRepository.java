package shop.photolancer.photolancer.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import shop.photolancer.photolancer.domain.Notice;
import shop.photolancer.photolancer.domain.enums.Category;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
    @Query("SELECT n FROM Notice n WHERE n.isPublic = true")
    Page<Notice> findAll(Pageable pageable);
    @Query("SELECT n FROM Notice n WHERE n.category = :category AND n.isPublic = true")
    Page<Notice> findAllByCategory(Category category, Pageable pageable);

    @Modifying
    @Transactional
    @Query("UPDATE Notice n SET n.category = :category, n.isPublic = :isPublic, n.content = :content, n.title = :title WHERE n.id = :id")
    void updateNotice(Long id, Category category, Boolean isPublic, String content, String title);
}
