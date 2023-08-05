package shop.photolancer.photolancer.web.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

public class FollowingResponseDto {

    @Getter
    @Builder
    public static class FollowingUserPostsDto{
        private Long id;
        private Integer level;
        private String nickname;
        private String profileUrl;
        private List<PostResponseDto.PostListDto> postList;
    }
}
