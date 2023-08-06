package shop.photolancer.photolancer.web.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;
import shop.photolancer.photolancer.domain.enums.Category;
import shop.photolancer.photolancer.domain.mapping.NoticeFile;

import java.util.List;

public class NoticeResponseDto {
    @Getter
    @Builder
    public static class NoticePagingDto {
        private Long id;
        private Category category;
        private  String title;
        private String createdAt;
    }

    @Getter
    @Builder
    public static class NoticeListDto {
        Page<NoticePagingDto> NoticePagingDto;
        Boolean isAdmin;
    }
    @Getter
    @Builder
    public static class NoticeDetailDto {
        private Long noticeId;
        private Category category;
        private String createdAt;
        private String title;
        private String content;
        private List<NoticeFileDto> noticeFileList;
        private Boolean isPublic;
        Boolean isAdmin;
    }

    @Getter
    @Builder
    public static class NoticeFileDto {
        private Long noticeFileId;
        private String uri;
    }
}
