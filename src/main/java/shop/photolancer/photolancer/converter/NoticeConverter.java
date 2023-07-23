package shop.photolancer.photolancer.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import shop.photolancer.photolancer.domain.Notice;
import shop.photolancer.photolancer.domain.enums.Category;
import shop.photolancer.photolancer.web.dto.NoticeResponseDto;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Component
public class NoticeConverter {

    public Notice toNotice(String content, String title, Category category, Boolean isPublic) {
        return Notice.builder()
                .content(content)
                .title(title)
                .category(category)
                .isPublic(isPublic)
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
}
