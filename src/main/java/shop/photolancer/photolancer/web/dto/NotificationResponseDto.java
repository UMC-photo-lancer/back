package shop.photolancer.photolancer.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Setter
@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "알림 반환 dto")
public class NotificationResponseDto {
    private LocalDateTime updatedAt;
    private String message;
    private String type;
}
