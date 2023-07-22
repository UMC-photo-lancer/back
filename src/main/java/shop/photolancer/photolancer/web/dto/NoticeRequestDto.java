package shop.photolancer.photolancer.web.dto;

import lombok.*;
import shop.photolancer.photolancer.domain.enums.Category;

public class NoticeRequestDto {
    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class NoticeUploadDto {
        private String content;
        private String title;
        private Category category;
        private Boolean isPublic;
    }
}
