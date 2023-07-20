package shop.photolancer.photolancer.web.dto;

import lombok.*;

public class PostRequestDto {

    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class PostUploadDto {
        //    private User user;
        private String content;
        private Integer likeCount;
        private Boolean isSale;
        private Integer point;
    }
}
