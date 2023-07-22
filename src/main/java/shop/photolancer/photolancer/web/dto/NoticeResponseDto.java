package shop.photolancer.photolancer.web.dto;

import lombok.Builder;
import lombok.Getter;
import shop.photolancer.photolancer.domain.enums.Category;

public class NoticeResponseDto {
    @Getter
    @Builder
    public static class NoticePagingDto {
        private Long id;
        private Category category;
        private  String title;
        private String createdAt;
    }
}
