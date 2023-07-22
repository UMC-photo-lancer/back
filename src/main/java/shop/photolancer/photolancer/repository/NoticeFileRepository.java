package shop.photolancer.photolancer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.photolancer.photolancer.domain.mapping.NoticeFile;

public interface NoticeFileRepository extends JpaRepository<NoticeFile, Long> {
}
