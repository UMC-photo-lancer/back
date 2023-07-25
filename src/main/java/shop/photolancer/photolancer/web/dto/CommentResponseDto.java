package shop.photolancer.photolancer.web.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

public class CommentResponseDto {
    @Getter
    @Builder
    public static class CommentsResponseDto {
        private long id;

        private String content;

        private List<CommentResponseDto.RecommentsResponseDto> recomments;

        private UserResponseDto.PostUserDto user;
    }

    @Getter
    @Builder
    public static class RecommentsResponseDto {
        private long id;

        private String content;

        private UserResponseDto.PostUserDto user;
    }
}
