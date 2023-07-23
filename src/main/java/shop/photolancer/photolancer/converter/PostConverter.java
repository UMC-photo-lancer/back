package shop.photolancer.photolancer.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import shop.photolancer.photolancer.domain.Bookmark;
import shop.photolancer.photolancer.domain.Post;
import shop.photolancer.photolancer.domain.mapping.PostBookmark;
import shop.photolancer.photolancer.domain.mapping.PostImage;
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
    public PostImage toPostImage(String uri, Post post) {
        return PostImage.builder()
                .uri(uri)
                .post(post)
                .build();
    }

    public PostBookmark toPostBookmark(Post post, Bookmark bookmark) {
        return PostBookmark.builder()
                .post(post)
                .bookmark(bookmark)
                .build();
    }

    public PostResponseDto.PostImageListDto toPostImageList(PostImage postImage) {
        return PostResponseDto.PostImageListDto.builder()
                .imageId(postImage.getId())
                .postId(postImage.getPost().getId())
                .likeCount(postImage.getPost().getLikeCount())
                .createdAt(postImage.getPost().getCreatedAt().toString().substring(0, 10))
                .uri(postImage.getUri())
//                .user(postImage.getPost().getUser())
                .build();
    }
}
