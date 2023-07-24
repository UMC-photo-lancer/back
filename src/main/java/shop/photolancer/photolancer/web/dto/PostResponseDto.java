package shop.photolancer.photolancer.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;
import shop.photolancer.photolancer.domain.Post;
import shop.photolancer.photolancer.domain.User;
import shop.photolancer.photolancer.domain.mapping.PostImage;

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
    public static class PostImageListDto {
        private Long imageId;
        private Integer likeCount;
        private String createdAt;
        private Long postId;
        private String uri;
//        private User user;
    }



}
