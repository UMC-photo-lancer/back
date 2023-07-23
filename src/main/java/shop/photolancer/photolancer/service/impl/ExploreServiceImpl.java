package shop.photolancer.photolancer.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import shop.photolancer.photolancer.converter.PostConverter;
import shop.photolancer.photolancer.domain.mapping.PostImage;
import shop.photolancer.photolancer.repository.ImgRepository;
import shop.photolancer.photolancer.service.ExploreService;
import shop.photolancer.photolancer.web.dto.PostResponseDto;


@Service
@RequiredArgsConstructor
public class ExploreServiceImpl implements ExploreService {
    private final ImgRepository imgRepository;
    private final PostConverter postConverter;
    @Override
    public Page<PostResponseDto.PostImageListDto> hotPhoto(Pageable request) {
        Page<PostImage> postImageList = imgRepository.findExplore(request);

        Page<PostResponseDto.PostImageListDto> hotPhotoPage = postImageList.map(
                hotPhoto -> postConverter.toPostImageList(hotPhoto)
        );
        return hotPhotoPage;
    }

    @Override
    public Page<PostResponseDto.PostImageListDto> recentPhoto(Pageable request) {
        Page<PostImage> postImageList = imgRepository.findExplore(request);

        Page<PostResponseDto.PostImageListDto> recentPhotoPage = postImageList.map(
                recentPhoto -> postConverter.toPostImageList(recentPhoto)
        );
        return recentPhotoPage;
    }
}
