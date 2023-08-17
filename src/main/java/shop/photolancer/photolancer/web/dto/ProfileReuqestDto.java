package shop.photolancer.photolancer.web.dto;

import lombok.*;

public class ProfileReuqestDto {
    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class profileReuqestDto {
        private Long userId;
    }
}
