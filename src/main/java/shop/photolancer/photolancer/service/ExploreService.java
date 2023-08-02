package shop.photolancer.photolancer.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import shop.photolancer.photolancer.web.dto.PostResponseDto;

public interface ExploreService {
    Page<PostResponseDto.PostListDto> hotPhoto(Pageable request);

    Page<PostResponseDto.PostListDto> recentPhoto(Pageable request);

    PostResponseDto.PostAwardsDto photoAwards();

    PostResponseDto.PostAwardsDto chagePhotoAward(Long id);
}
