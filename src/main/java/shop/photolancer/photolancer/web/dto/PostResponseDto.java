package shop.photolancer.photolancer.web.dto;

import lombok.Builder;
import lombok.Getter;
import shop.photolancer.photolancer.domain.User;

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
    @Getter
    @Builder
    public static class PostListDto {
        private Long postId;
        private Integer likeCount;
        private String createdAt;
        private String thumbNailUri;
        private boolean isSale;
//        private User user;
    }
}
