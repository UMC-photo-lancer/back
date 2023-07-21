package shop.photolancer.photolancer.service;

import shop.photolancer.photolancer.web.dto.PostResponseDto;

import java.util.List;

public interface PostService {
    void upload(String content, Integer likeCount, Boolean isSale,
                Integer point, List<String> imgPaths, List<String> bookmarkList);

    PostResponseDto.PostDetailDto searchById(Long postId);
}
