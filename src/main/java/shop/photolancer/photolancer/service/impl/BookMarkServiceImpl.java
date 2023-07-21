package shop.photolancer.photolancer.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.photolancer.photolancer.converter.BookmarkConverter;
import shop.photolancer.photolancer.domain.Bookmark;
import shop.photolancer.photolancer.repository.BookmarkRepository;
import shop.photolancer.photolancer.service.BookMarkService;


@Service
@RequiredArgsConstructor
public class BookMarkServiceImpl implements BookMarkService {
    private final BookmarkRepository bookMarkRepository;
    private final BookmarkConverter bookMarkConverter;

    public Bookmark createBookmark(String bookmarkName) {
        Bookmark bookmark = bookMarkRepository.findByName(bookmarkName);

        if (bookmark == null) {
            return bookMarkRepository.save(bookMarkConverter.toBookmark(bookmarkName));
        }
        return bookmark;
    }
}
