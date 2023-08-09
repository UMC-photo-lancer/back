package shop.photolancer.photolancer.web.dto;

import lombok.*;

import java.util.List;

public class PostRequestDto {

    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class PostUploadDto {
        private String content;
        private Integer likeCount;
        private Boolean isSale;
        private Integer point;
        private List<String> bookmark;
    }

    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class PostShareDto {
            private List<Long> userId;
    }
}
