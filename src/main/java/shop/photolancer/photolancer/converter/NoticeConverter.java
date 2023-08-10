package shop.photolancer.photolancer.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import shop.photolancer.photolancer.domain.Notice;
import shop.photolancer.photolancer.domain.User;
import shop.photolancer.photolancer.domain.enums.Category;
import shop.photolancer.photolancer.domain.mapping.NoticeFile;
import shop.photolancer.photolancer.web.dto.NoticeRequestDto;
import shop.photolancer.photolancer.web.dto.NoticeResponseDto;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Component
public class NoticeConverter {

    public Notice toNotice(String content, String title, Category category, Boolean isPublic, User user) {
        return Notice.builder()
                .content(content)
                .title(title)
                .category(category)
                .isPublic(isPublic)
                .user(user)
                .build();
    }

    public NoticeResponseDto.NoticePagingDto toNoticePage(Long id, String title,
                                                          LocalDateTime createdAt, Category category) {
        return NoticeResponseDto.NoticePagingDto.builder()
                .id(id)
                .title(title)
                .createdAt(String.valueOf(createdAt).toString().substring(0, 10))
                .category(category)
                .build();
    }
    public NoticeResponseDto.NoticeListDto toNoticeList(Page<NoticeResponseDto.NoticePagingDto> noticePagingDto,
                                                        Boolean isAdmin) {
        return NoticeResponseDto.NoticeListDto.builder()
                .isAdmin(isAdmin)
                .NoticePagingDto(noticePagingDto)
                .build();
    }

    public NoticeResponseDto.NoticeDetailDto toNoticeDetail(Notice notice, List<NoticeResponseDto.NoticeFileDto> noticeFileList, Boolean isAdmin) {
        return NoticeResponseDto.NoticeDetailDto.builder()
                .noticeId(notice.getId())
                .category(notice.getCategory())
                .content(notice.getContent())
                .noticeFileList(noticeFileList)
                .createdAt(notice.getCreatedAt().toString().substring(0, 10))
                .isAdmin(isAdmin)
                .title(notice.getTitle())
                .isPublic(notice.getIsPublic())
                .build();
    }

    public NoticeResponseDto.NoticeFileDto toNoticeFile(NoticeFile noticeFile) {
        return NoticeResponseDto.NoticeFileDto.builder()
                .noticeFileId(noticeFile.getId())
                .uri(noticeFile.getUri())
                .build();
    }
}
