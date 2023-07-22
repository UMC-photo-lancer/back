package shop.photolancer.photolancer.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import shop.photolancer.photolancer.domain.Notice;
import shop.photolancer.photolancer.domain.enums.Category;

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
}
