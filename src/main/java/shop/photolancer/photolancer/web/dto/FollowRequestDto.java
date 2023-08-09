package shop.photolancer.photolancer.web.dto;

import lombok.*;

public class FollowRequestDto {

    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class RequestFollowDto {
        private Long followingUserId;
    }

    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class DeleteFollowerDto {
        private Long followerUserId;
    }
}
