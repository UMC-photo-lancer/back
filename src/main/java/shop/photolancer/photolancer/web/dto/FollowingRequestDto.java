package shop.photolancer.photolancer.web.dto;

import lombok.*;

public class FollowingRequestDto {

    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class RequestFollowDto {
        private Long followingUserId;
    }
}
