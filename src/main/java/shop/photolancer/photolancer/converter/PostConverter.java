package shop.photolancer.photolancer.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import shop.photolancer.photolancer.domain.Bookmark;
import shop.photolancer.photolancer.domain.Contest;
import shop.photolancer.photolancer.domain.Post;
import shop.photolancer.photolancer.domain.User;
import shop.photolancer.photolancer.domain.mapping.*;
import shop.photolancer.photolancer.web.dto.PostResponseDto;

import java.util.List;

@RequiredArgsConstructor
@Component
public class PostConverter {
    private final UserConverter userConverter;
    public Post toPost(String content, Integer likeCount, Integer point,
                       Boolean isSale, String thumbNailUri, User user) {
        return Post.builder()
                .user(user)
                .content(content)
                .likeCount(likeCount)
                .isSale(isSale)
                .point(point)
                .thumbNailUri(thumbNailUri)
                .build();
    }

    public PostResponseDto.PostDetailDto toPostDetail(Post post, List<String> postImageUri,
                                                      List<String> postBookmarkName,
                                                       Boolean isUserPhoto) {
        return PostResponseDto.PostDetailDto.builder()
                .content(post.getContent())
                .isSale(post.getIsSale())
                .likeCount(post.getLikeCount())
                .point(post.getPoint())
                .postImages(postImageUri)
                .bookmarks(postBookmarkName)
                .isUserPhoto(isUserPhoto)
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

    public PostResponseDto.PostListDto toPostList(Post post, Boolean isUserPhoto) {
        return PostResponseDto.PostListDto.builder()
                .postId(post.getId())
                .likeCount(post.getLikeCount())
                .createdAt(post.getCreatedAt().toString().substring(0, 10))
                .thumbNailUri(post.getThumbNailUri())
                .isSale(post.getIsSale())
                .user(userConverter.toUserProfile(post.getUser()))
                .isUserPhoto(isUserPhoto)
                .build();
    }

    public PostLike toPostLike(Post post, User user) {
        return PostLike.builder()
                .user(user)
                .post(post)
                .build();
    }
    // awards에 보이는 페이지
    public PostResponseDto.PostAwardsDto toPostAwardsDto(Contest contest, List<PostResponseDto.PostContestDto> postContests,
                                                         List<Contest> contestList) {
        return PostResponseDto.PostAwardsDto.builder()
                .contest(contest)
                .postContests(postContests)
                .contestList(contestList)
                .build();
    }
    // postContest의 dto
    public PostResponseDto.PostContestDto toPostContestDto(PostContest postContest, PostResponseDto.PostListDto post) {
        return PostResponseDto.PostContestDto.builder()
                .post(post)
                .id(postContest.getId())
                .ranked(postContest.getRanked())
                .build();
    }

    public PostResponseDto.UserPhotoDto toUserPhotoDto(UserPhoto userPhoto) {
        return PostResponseDto.UserPhotoDto.builder()
                .id(userPhoto.getId())
                .build();
    }

    public SavedPost toSavedPost(User user, Post post) {
        return SavedPost.builder()
                .post(post)
                .user(user)
                .build();
    }

    public PostResponseDto.PostExploreDto toPostExploreDto(List<PostResponseDto.PostListDto> hotPhotoPage,
                                                           List<PostResponseDto.PostListDto> recentPhotoPage,
                                                           PostResponseDto.PostAwardsDto awardPhotoList) {
        return PostResponseDto.PostExploreDto.builder()
                .awardPhotoList(awardPhotoList)
                .hotPhotoList(hotPhotoPage)
                .recentPhotoList(recentPhotoPage)
                .build();
    }
}
