package shop.photolancer.photolancer.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.photolancer.photolancer.service.impl.BookmarkServiceImpl;
import shop.photolancer.photolancer.service.impl.PostBookmarkServiceImpl;
import shop.photolancer.photolancer.web.dto.BookmarkResponseDto;
import shop.photolancer.photolancer.web.dto.PostResponseDto;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/bookmark")
public class BookmarkController {
    private final PostBookmarkServiceImpl postBookmarkService;
    @GetMapping
    public Page<PostResponseDto.PostListDto> bookmarkDefault(@PageableDefault(size = 12, sort = "post.likeCount", direction = Sort.Direction.DESC)
                                                      Pageable request) {
        return postBookmarkService.postBookmarkDefaultList(request);
    }
    @GetMapping("/popular")
    public Page<PostResponseDto.PostListDto> bookmarkDefaultpopular(@PageableDefault(size = 12, sort = "post.likeCount", direction = Sort.Direction.DESC)
                                                             Pageable request) {
        return postBookmarkService.postBookmarkDefaultList(request);
    }
    @GetMapping("/recent")
    public Page<PostResponseDto.PostListDto> bookmarkDefaultRecent(@PageableDefault(size = 12, sort = "post.createdAt", direction = Sort.Direction.DESC)
                                                             Pageable request) {
        return postBookmarkService.postBookmarkDefaultList(request);
    }

    @GetMapping("/{bookmarkName}")
    public Page<PostResponseDto.PostListDto> bookmarkPhoto(@PageableDefault(size = 12, sort = "post.likeCount",
            direction = Sort.Direction.DESC) Pageable request, @PathVariable String bookmarkName) {
        return postBookmarkService.postBookmarkList(request, bookmarkName);
    }

    @GetMapping("/{bookmarkName}/recent")
    public Page<PostResponseDto.PostListDto> bookmarkRecentPhoto(@PageableDefault(size = 12, sort = "post.createdAt",
            direction = Sort.Direction.DESC) Pageable request, @PathVariable String bookmarkName) {
        return postBookmarkService.postBookmarkList(request, bookmarkName);
    }

    @GetMapping("/{bookmarkName}/popular")
    public Page<PostResponseDto.PostListDto> bookmarkPopularPhoto(@PageableDefault(size = 12, sort = "post.likeCount",
            direction = Sort.Direction.DESC) Pageable request, @PathVariable String bookmarkName) {
        return postBookmarkService.postBookmarkList(request, bookmarkName);
    }

    @GetMapping("/data")
    public List<BookmarkResponseDto.BookmarkDataResponseDto> bookmarkData() {
        return postBookmarkService.getBookmarkData();
    }
}