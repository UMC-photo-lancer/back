package shop.photolancer.photolancer.web.dto;

import lombok.Builder;
import lombok.Getter;

public class UserResponseDto {

    @Getter
    @Builder
    public static class ChatUserDto{
        private Long id;
        private Integer level;
        private String nickname;
        private String profileUrl;
    }

}
