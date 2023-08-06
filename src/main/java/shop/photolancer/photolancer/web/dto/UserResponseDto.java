package shop.photolancer.photolancer.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class UserResponseDto {

    @Getter @Setter
    @Builder
    public static class ChatUserDto {
        private Long id;
        private Integer level;
        private String nickname;
        private String profileUrl;

        public ChatUserDto(Long id, Integer level, String nickname, String profileUrl) {
            this.id = id;
            this.level = level;
            this.nickname = nickname;
            this.profileUrl = profileUrl;
        }
    }

    @Getter
    @Builder
    public static class PostUserDto{
        private Long id;
        private Integer level;
        private String nickname;
        private String profileUrl;
    }
}
