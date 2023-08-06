package shop.photolancer.photolancer.service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import shop.photolancer.photolancer.domain.Notice;
import shop.photolancer.photolancer.domain.User;
import shop.photolancer.photolancer.domain.enums.Category;
import shop.photolancer.photolancer.domain.mapping.NoticeFile;
import shop.photolancer.photolancer.web.dto.NoticeResponseDto;

import java.util.List;

public interface NoticeService {
    Long upload(String content, String title, Category category, Boolean isPublic, List<String> filePaths, User user);
    Page<NoticeResponseDto.NoticePagingDto> noticePage(Pageable request);
    Page<NoticeResponseDto.NoticePagingDto> noticePageCategory(Category category, Pageable request);
    Long upload(String content, String title, Category category, Boolean isPublic, User user);

    Notice findNoticeById(Long id);

    List<NoticeResponseDto.NoticeFileDto> findNoticeFileByNotice(Notice notice);
}
