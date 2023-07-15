package shop.photolancer.photolancer.web.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

public class ChatResponseDto {

    @Getter
    @Builder
    public static class ChatResponse {
        private long id;
        private UserResponseDto.ChatUserDto sender;
    }

    @Getter
    @Builder
    public static class MessageResponse { // 채팅방 속 하나의 메세지
        private long id;
        private UserResponseDto.ChatUserDto user;
        private String content;
        private LocalDateTime createdAt;
    }

}
