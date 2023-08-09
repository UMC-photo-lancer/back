package shop.photolancer.photolancer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.photolancer.photolancer.domain.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
