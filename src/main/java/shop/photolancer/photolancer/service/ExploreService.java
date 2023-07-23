package shop.photolancer.photolancer.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import shop.photolancer.photolancer.web.dto.PostResponseDto;

public interface ExploreService {
    Page<PostResponseDto.PostImageListDto> hotPhoto(Pageable request);

    Page<PostResponseDto.PostImageListDto> recentPhoto(Pageable request);
}
