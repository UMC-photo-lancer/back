package shop.photolancer.photolancer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.photolancer.photolancer.domain.Notice;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
}
