package shop.photolancer.photolancer.web.dto;

import lombok.*;

public class BookmarkResponseDto {
    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class BookmarkDataResponseDto {
        private Long id;
        private String bookmarkName;
        private Long postNum;
    }
}
