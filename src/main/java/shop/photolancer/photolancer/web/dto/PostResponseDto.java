package shop.photolancer.photolancer.web.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

public class PostResponseDto {
    @Getter
    @Builder
    public static class PostDetailDto {
        private String content;
        private Boolean isSale;
        private Integer likeCount;
        private Integer point;
        private List<String> postImages;
        private List<String> bookmarks;
    }
}
