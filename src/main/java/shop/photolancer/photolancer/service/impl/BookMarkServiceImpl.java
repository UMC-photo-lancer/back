package shop.photolancer.photolancer.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.photolancer.photolancer.converter.BookMarkConverter;
import shop.photolancer.photolancer.domain.Bookmark;
import shop.photolancer.photolancer.repository.BookMarkRepository;
import shop.photolancer.photolancer.service.BookMarkService;


@Service
@RequiredArgsConstructor
public class BookMarkServiceImpl implements BookMarkService {
    private final BookMarkRepository bookMarkRepository;
    private final BookMarkConverter bookMarkConverter;

    public Bookmark createBookmark(String bookmarkName) {
        Bookmark bookmark = bookMarkRepository.findByName(bookmarkName);

        if (bookmark == null) {
            return bookMarkRepository.save(bookMarkConverter.toBookMark(bookmarkName));
        }
        return bookmark;
    }
}
