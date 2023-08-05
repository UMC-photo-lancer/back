package shop.photolancer.photolancer.service;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import shop.photolancer.photolancer.domain.Post;
import shop.photolancer.photolancer.web.dto.PostResponseDto;


public interface PostBookmarkService {
    Page<PostResponseDto.PostListDto> postBookmarkList (Pageable request, String bookmarkName);

    Page<PostResponseDto.PostListDto> postBookmarkDefaultList (Pageable request);
}
