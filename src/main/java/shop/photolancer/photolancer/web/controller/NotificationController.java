package shop.photolancer.photolancer.web.controller;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import shop.photolancer.photolancer.domain.Notification;
import shop.photolancer.photolancer.domain.enums.NotificationType;
import shop.photolancer.photolancer.service.impl.NotificationServiceImpl;
import shop.photolancer.photolancer.service.impl.UserServiceImpl;
import shop.photolancer.photolancer.web.dto.NotificationResponseDto;

import java.util.List;

@Api(tags = "알림 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notification")
@Slf4j
public class NotificationController {
    private final NotificationServiceImpl notificationService;

    @Operation(summary = "공지를 반환합니다.")
    @GetMapping(value = "list")
    public List<NotificationResponseDto> getNotificationsByType(@RequestParam String type) {
        NotificationType notificationType = NotificationType.fromString(type);
        return notificationService.getNotificationsByType(notificationType);
    }

    @Operation(summary = "전체공지를 반환합니다.")
    @GetMapping(value = "list_all")
    public List<NotificationResponseDto> getNotificationsByType() {
        return notificationService.getNotifications();
    }
}
