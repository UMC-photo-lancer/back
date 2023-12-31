package shop.photolancer.photolancer.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import shop.photolancer.photolancer.domain.Notification;
import shop.photolancer.photolancer.domain.User;
import shop.photolancer.photolancer.domain.enums.NotificationType;
import shop.photolancer.photolancer.repository.NotificationRepository;
import shop.photolancer.photolancer.web.dto.NotificationResponseDto;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl {

    private final NotificationRepository notificationRepository;
    public List<NotificationResponseDto> getNotificationsByType(NotificationType type, Long userId) {
        List<Notification> notifications;

        if (type == NotificationType.SYSTEM) {
            notifications = notificationRepository.findAllByType(type);
        } else {
            notifications = notificationRepository.findAllByTypeAndUserId(type, userId);
        }

        return notifications.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private NotificationResponseDto toDto(Notification notification) {
        return NotificationResponseDto.builder()
                .updatedAt(notification.getUpdatedAt())
                .message(notification.getMessage())
                .type(notification.getType().getNotification()) // NotificationType에서 실제 문자열 값을 가져옵니다.
                .post_uri(notification.getPostUri())
                .build();
    }

    public List<NotificationResponseDto> getNotifications(Long userId) {
        List<Notification> notifications = notificationRepository.findAllByUserIdOrTypeIsSystem(userId);

        return notifications.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
