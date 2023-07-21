package shop.photolancer.photolancer.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import shop.photolancer.photolancer.domain.Bookmark;

@RequiredArgsConstructor
@Component
public class BookmarkConverter {
    public Bookmark toBookmark(String bookmarkName) {
        return Bookmark.builder()
                .name(bookmarkName)
                .build();
    }
}
