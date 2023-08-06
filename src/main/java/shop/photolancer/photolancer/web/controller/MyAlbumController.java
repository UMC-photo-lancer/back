package shop.photolancer.photolancer.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.photolancer.photolancer.service.impl.PostServiceImpl;
import shop.photolancer.photolancer.web.dto.PostResponseDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/my-profile/album")
public class MyAlbumController {
    public final PostServiceImpl postService;
    @GetMapping("/mypost")
    public Page<PostResponseDto.PostListDto> myPosts(@PageableDefault(size = 12, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        try {
            return postService.myPosts(pageable);

        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }
    @GetMapping("/saved-post")
    public Page<PostResponseDto.PostListDto> savedPosts(@PageableDefault(size = 12, sort = "post.createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        try {
            return postService.savedPosts(pageable);
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

}
