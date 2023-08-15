package shop.photolancer.photolancer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import shop.photolancer.photolancer.domain.Notification;
import shop.photolancer.photolancer.domain.enums.NotificationType;
import shop.photolancer.photolancer.web.dto.NotificationResponseDto;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
//    @Query("SELECT new shop.photolancer.photolancer.web.dto.NotificationResponseDto(n.updatedAt, n.message, n.type) FROM Notification n WHERE n.type = :type")
    List<NotificationResponseDto> findNotificationsByType(NotificationType type);

    List<Notification> findAllByType(NotificationType type);
}