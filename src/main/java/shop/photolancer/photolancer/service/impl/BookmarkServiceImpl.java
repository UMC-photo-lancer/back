package shop.photolancer.photolancer.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.photolancer.photolancer.converter.BookmarkConverter;
import shop.photolancer.photolancer.domain.Bookmark;
import shop.photolancer.photolancer.repository.BookmarkRepository;
import shop.photolancer.photolancer.service.BookmarkService;


@Service
@RequiredArgsConstructor
public class BookmarkServiceImpl implements BookmarkService {
    private final BookmarkRepository bookmarkRepository;
    private final BookmarkConverter bookmarkConverter;

    public Bookmark createBookmark(String bookmarkName) {
        Bookmark bookmark = bookmarkRepository.findByName(bookmarkName);

        if (bookmark == null) {
            return bookmarkRepository.save(bookmarkConverter.toBookmark(bookmarkName));
        }
        return bookmark;
    }

    public Bookmark findBookmark(String bookmarkName) {
        return bookmarkRepository.findByName(bookmarkName);
    }
}
