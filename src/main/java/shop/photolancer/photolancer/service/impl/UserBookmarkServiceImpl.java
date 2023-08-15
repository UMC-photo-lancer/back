package shop.photolancer.photolancer.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import shop.photolancer.photolancer.domain.Bookmark;
import shop.photolancer.photolancer.domain.User;
import shop.photolancer.photolancer.domain.mapping.UserBookmark;
import shop.photolancer.photolancer.repository.BookmarkRepository;
import shop.photolancer.photolancer.repository.UserBookmarkRepository;
import shop.photolancer.photolancer.repository.UserRepository;
import shop.photolancer.photolancer.web.dto.BookmarkDto;

import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserBookmarkServiceImpl {
    protected final UserRepository userRepository;
    protected final BookmarkRepository bookmarkRepository;
    protected final UserBookmarkRepository userBookmarkRepository;

    public void registerBookmark(User user, BookmarkDto bookmarkDto) {
        for (String content : bookmarkDto.getContent()) {
            Bookmark existingBookmark = bookmarkRepository.findByName(content);

            if (existingBookmark == null) {
                Bookmark newBookmark = Bookmark.builder()
                        .name(content)
                        .build();
                bookmarkRepository.save(newBookmark);
                existingBookmark = newBookmark;
            }

            List<UserBookmark> existingUserBookmarks = userBookmarkRepository.findByUserAndBookmark(user, existingBookmark);
            if (existingUserBookmarks.isEmpty()) {
                UserBookmark userBookmark = UserBookmark.builder()
                        .user(user)
                        .bookmark(existingBookmark)
                        .content(content)
                        .build();
                userBookmarkRepository.save(userBookmark);
            }
        }
    }

    public List<BookmarkDto> findBookmarksByUser(User user) {
        Long userId = user.getId();
        List<UserBookmark> userBookmarks = userBookmarkRepository.findByUserId(userId);

        return userBookmarks.stream()
                .map(ub -> new BookmarkDto(Collections.singletonList(ub.getBookmark().getName())))
                .collect(Collectors.toList());
    }

    public void deleteBookmark(User user, BigInteger bookmarkId) {
    }
}