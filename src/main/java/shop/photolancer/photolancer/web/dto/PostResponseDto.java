package shop.photolancer.photolancer.web.dto;

import lombok.Builder;
import lombok.Getter;
import shop.photolancer.photolancer.domain.Contest;
import shop.photolancer.photolancer.domain.Post;
import shop.photolancer.photolancer.domain.User;
import shop.photolancer.photolancer.domain.enums.Ranked;
import shop.photolancer.photolancer.domain.mapping.PostContest;
import shop.photolancer.photolancer.domain.mapping.UserPhoto;

import javax.persistence.*;
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
    }
    @Getter
    @Builder
    public static class PostListDto {
        private Long postId;
        private Integer likeCount;
        private String createdAt;
        private String thumbNailUri;
        private boolean isSale;
//        private User user;
        private Boolean isUserPhoto;
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
}
