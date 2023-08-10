package shop.photolancer.photolancer.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import shop.photolancer.photolancer.domain.Bookmark;
import shop.photolancer.photolancer.web.dto.BookmarkResponseDto;

@RequiredArgsConstructor
@Component
public class BookmarkConverter {
    public Bookmark toBookmark(String bookmarkName) {
        return Bookmark.builder()
                .name(bookmarkName)
                .build();
    }

    public BookmarkResponseDto.BookmarkDataResponseDto toBookmarkData(Bookmark bookmark, Long postNum) {
        return BookmarkResponseDto.BookmarkDataResponseDto.builder()
                .id(bookmark.getId())
                .bookmarkName(bookmark.getName())
                .postNum(postNum)
                .build();
    }
}
