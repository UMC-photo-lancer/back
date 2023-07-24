package shop.photolancer.photolancer.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import shop.photolancer.photolancer.converter.PostConverter;
import shop.photolancer.photolancer.domain.Post;
import shop.photolancer.photolancer.repository.PostRepository;
import shop.photolancer.photolancer.service.ExploreService;
import shop.photolancer.photolancer.web.dto.PostResponseDto;


@Service
@RequiredArgsConstructor
public class ExploreServiceImpl implements ExploreService {
    private final PostConverter postConverter;
    private final PostRepository postRepository;
    @Override
    public Page<PostResponseDto.PostListDto> hotPhoto(Pageable request) {
        Page<Post> postImageList = postRepository.findAll(request);

        Page<PostResponseDto.PostListDto> hotPhotoPage = postImageList.map(
                hotPhoto -> postConverter.toPostList(hotPhoto)
        );
        return hotPhotoPage;
    }

    @Override
    public Page<PostResponseDto.PostListDto> recentPhoto(Pageable request) {
        Page<Post> postImageList = postRepository.findAll(request);

        Page<PostResponseDto.PostListDto> recentPhotoPage = postImageList.map(
                recentPhoto -> postConverter.toPostList(recentPhoto)
        );
        return recentPhotoPage;
    }
}
