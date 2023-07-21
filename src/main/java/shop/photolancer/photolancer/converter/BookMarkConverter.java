package shop.photolancer.photolancer.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import shop.photolancer.photolancer.domain.Bookmark;

@RequiredArgsConstructor
@Component
public class BookMarkConverter {
    public Bookmark toBookMark(String bookmarkName) {
        return Bookmark.builder()
                .name(bookmarkName)
                .build();
    }
}
