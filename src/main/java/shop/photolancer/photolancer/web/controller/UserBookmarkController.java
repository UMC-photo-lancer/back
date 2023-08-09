package shop.photolancer.photolancer.web.controller;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.web.bind.annotation.*;
import shop.photolancer.photolancer.domain.User;
import shop.photolancer.photolancer.domain.mapping.UserBookmark;
import shop.photolancer.photolancer.service.impl.UserServiceImpl;
import shop.photolancer.photolancer.service.impl.UserBookmarkServiceImpl;
import shop.photolancer.photolancer.web.dto.BookmarkDto;

import java.util.List;

@Api(tags = "사용자 북마크 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/bookmark")
@Slf4j
public class UserBookmarkController {

    private final UserServiceImpl userServiceImpl;
    private final UserBookmarkServiceImpl userBookmarkServiceImpl;

    @Operation(summary = "사용자의 북마크를 등록합니다.")
    @PostMapping(value = "/register")
    public ResponseEntity<?> registerBoookmark(@RequestBody BookmarkDto bookmarkDto) {
        try {
            User user = userServiceImpl.getCurrentUser();
            userBookmarkServiceImpl.registerBookmark(user, bookmarkDto);
            return ResponseEntity.ok().build();
        }catch (AuthenticationCredentialsNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @Operation(summary = "사용자의 북마크를 반환합니다.")
    @GetMapping(value = "/list")
    public ResponseEntity<?> getUserBookmarks() {
        try {
            User user = userServiceImpl.getCurrentUser();
            List<BookmarkDto> bookmarks = userBookmarkServiceImpl.findBookmarksByUser(user);
            return ResponseEntity.ok(bookmarks);
        } catch (AuthenticationCredentialsNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }
}
