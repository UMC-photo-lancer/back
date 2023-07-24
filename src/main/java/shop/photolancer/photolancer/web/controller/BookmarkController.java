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
import shop.photolancer.photolancer.domain.Post;
import shop.photolancer.photolancer.service.impl.PostBookmarkServiceImpl;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bookmark")
public class BookmarkController {
    public final PostBookmarkServiceImpl postBookmarkService;

    @GetMapping("/{bookmarkName}")
    public Page<Post> bookmarkPhoto(@PageableDefault(size = 12, sort = "post.likeCount",
            direction = Sort.Direction.DESC) Pageable request, @PathVariable String bookmarkName) {
        return postBookmarkService.postBookmarkList(request, bookmarkName);
    }

    @GetMapping("/{bookmarkName}/recent")
    public Page<Post> bookmarkRecentPhoto(@PageableDefault(size = 12, sort = "post.createdAt",
            direction = Sort.Direction.DESC) Pageable request, @PathVariable String bookmarkName) {
        return postBookmarkService.postBookmarkList(request, bookmarkName);
    }

    @GetMapping("/{bookmarkName}/popular")
    public Page<Post> bookmarkPopularPhoto(@PageableDefault(size = 12, sort = "post.likeCount",
            direction = Sort.Direction.DESC) Pageable request, @PathVariable String bookmarkName) {
        return postBookmarkService.postBookmarkList(request, bookmarkName);
    }
}