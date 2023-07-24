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
        System.out.println(bookmarkName);
        return postBookmarkService.postBookmarkList(request, bookmarkName);
    }
}