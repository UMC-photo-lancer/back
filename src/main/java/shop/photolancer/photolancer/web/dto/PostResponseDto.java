package shop.photolancer.photolancer.web.dto;

import lombok.Builder;
import lombok.Getter;
import shop.photolancer.photolancer.domain.Contest;
import shop.photolancer.photolancer.domain.enums.Ranked;

import java.util.List;

public class PostResponseDto {
    @Getter
    @Builder
    public static class PostDetailDto {
        private String content;
        private Boolean isSale;
        private Integer likeCount;
        private Integer point;
        private List<String> postImages;
        private List<String> bookmarks;
        private Boolean isUserPhoto;
        private Boolean likeStatus;
        private Boolean isSavedPost;
        private UserResponseDto.PostUserDto user;
    }
    @Getter
    @Builder
    public static class PostListDto {
        private Long postId;
        private Integer likeCount;
        private String createdAt;
        private String thumbNailUri;
        private boolean isSale;
        private UserResponseDto.PostUserDto user;
        private Boolean isUserPhoto;
        private Boolean likeStatus;
        private Boolean isSavedPost;
    }

    @Getter
    @Builder
    public static class PostAwardsDto {
        private Contest contest;
        private List<PostResponseDto.PostContestDto> postContests;
        private List<Contest> contestList;
    }

    @Getter
    @Builder
    public static class PostContestDto {
        private Long id;
        private Ranked ranked;
        private PostResponseDto.PostListDto post;
    }

    @Getter
    @Builder
    public static class UserPhotoDto {
        private Long id;
    }

    @Getter
    @Builder
    public static class PostExploreDto {
        private List<PostResponseDto.PostListDto> hotPhotoList;
        private List<PostResponseDto.PostListDto> recentPhotoList;
        private PostResponseDto.PostAwardsDto awardPhotoList;
    }
}
