package shop.photolancer.photolancer.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import shop.photolancer.photolancer.domain.*;
import shop.photolancer.photolancer.domain.enums.NotificationType;
import shop.photolancer.photolancer.domain.enums.PostStatus;
import shop.photolancer.photolancer.domain.mapping.*;
import shop.photolancer.photolancer.web.dto.PostRequestDto;
import shop.photolancer.photolancer.web.dto.PostResponseDto;

import java.util.List;

@RequiredArgsConstructor
@Component
public class PostConverter {
    private final UserConverter userConverter;

    public Post toPost(PostRequestDto.PostUploadDto post, String thumbNailUri, User user) {
        return Post.builder()
                .user(user)
                .content(post.getContent())
                .likeCount(post.getLikeCount())
                .isSale(post.getIsSale())
                .point(post.getPoint())
                .thumbNailUri(thumbNailUri)
                .postStatus(PostStatus.EXISTENT)
                .build();
    }

    public PostResponseDto.PostDetailDto toPostDetail(Post post, List<String> postImageUri,
                                                      List<String> postBookmarkName,
                                                      Boolean isUserPhoto,
                                                      Boolean likeStatus,
                                                      Boolean isSavedPost) {
        return PostResponseDto.PostDetailDto.builder()
                .content(post.getContent())
                .isSale(post.getIsSale())
                .likeCount(post.getLikeCount())
                .point(post.getPoint())
                .postImages(postImageUri)
                .bookmarks(postBookmarkName)
                .isUserPhoto(isUserPhoto)
                .likeStatus(likeStatus)
                .isSavedPost(isSavedPost)
                .user(userConverter.toUserProfile(post.getUser()))
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

    public PostResponseDto.PostListDto toPostList(Post post, Boolean isUserPhoto, Boolean isSavedPost,
                                                  Boolean likeStatus) {
        return PostResponseDto.PostListDto.builder()
                .postId(post.getId())
                .likeCount(post.getLikeCount())
                .createdAt(post.getCreatedAt().toString().substring(0, 10))
                .thumbNailUri(post.getThumbNailUri())
                .isSale(post.getIsSale())
                .user(userConverter.toUserProfile(post.getUser()))
                .isUserPhoto(isUserPhoto)
                .isSavedPost(isSavedPost)
                .likeStatus(likeStatus)
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

    public Notification toShareNotification(User sharedBy, User shareTo, Post post){
        return Notification.builder()
                .message("Lv. " + sharedBy.getLevel() + " " + sharedBy.getNickname() + "님이 "+ post.getUser().getNickname()+"님의 게시글을 공유하였습니다.")
                .type(NotificationType.SHARE)
                .user(shareTo)
                .postUri("http://photolancer.shop/post/" + post.getId())
                .build();
    }

    public Notification toLikeNotification(User currentUser, User postOwner){
        return Notification.builder()
                .message("Lv. " + currentUser.getLevel() + " " + currentUser.getNickname() + "님이 " + postOwner.getNickname() + "님의 게시글을 좋아합니다.")
                .type(NotificationType.HEART)
                .user(postOwner)
                .build();
    }
}
