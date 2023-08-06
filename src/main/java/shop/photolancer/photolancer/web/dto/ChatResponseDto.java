package shop.photolancer.photolancer.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public class ChatResponseDto {

    @Getter @Setter
    @Builder
    public static class ChatResponse {
        private long id;
        private UserResponseDto.ChatUserDto sender;
        private MessageResponse lastMessage;
    }

    @Getter @Setter
    @Builder
    public static class MessageResponse {
        private Long id;
        private Long senderId;
        private String content;
        private LocalDateTime createdAt;

        // 생성자를 public으로 선언
        public MessageResponse(long id, Long senderId, String content, LocalDateTime createdAt) {
            this.id = id;
            this.senderId = senderId;
            this.content = content;
            this.createdAt = createdAt;
        }
    }
}
