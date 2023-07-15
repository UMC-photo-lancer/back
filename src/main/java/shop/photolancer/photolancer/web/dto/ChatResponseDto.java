package shop.photolancer.photolancer.web.dto;

import lombok.Builder;
import lombok.Getter;

public class ChatResponseDto {

    @Getter
    @Builder
    public static class ChatResponse {
        private long id;
        private UserResponseDto.ChatUserDto sender;
    }

}
