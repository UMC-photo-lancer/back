package shop.photolancer.photolancer.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.photolancer.photolancer.service.impl.ExploreServiceImpl;
import shop.photolancer.photolancer.web.dto.PostResponseDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/explore")
public class ExploreController {
    public final ExploreServiceImpl exploreService;
    @GetMapping("/hot")
    public Page<PostResponseDto.PostImageListDto> hotPhoto(@PageableDefault(size = 12, sort = "post.likeCount", direction = Sort.Direction.DESC)
                                                   Pageable request) {
        return exploreService.hotPhoto(request);
    }
}
