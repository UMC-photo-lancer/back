package shop.photolancer.photolancer.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import shop.photolancer.photolancer.domain.Post;
import shop.photolancer.photolancer.web.dto.PostResponseDto;

import java.util.List;

@RequiredArgsConstructor
@Component
public class PostConverter {
    public Post toPost(String content, Integer likeCount, Integer point, Boolean isSale) {
        return Post.builder()
//                .user(user)
                .content(content)
                .likeCount(likeCount)
                .isSale(isSale)
                .point(point)
                .build();
    }

    public PostResponseDto.PostDetailDto toPostDetail(Post post, List<String> postImageUri,
                                                      List<String> postBookmarkName) {
        return PostResponseDto.PostDetailDto.builder()
                .content(post.getContent())
                .isSale(post.getIsSale())
                .likeCount(post.getLikeCount())
                .point(post.getPoint())
                .postImages(postImageUri)
                .bookmarks(postBookmarkName)
                .build();
    }
}
