package shop.photolancer.photolancer.web.dto;

import lombok.*;
import shop.photolancer.photolancer.domain.enums.NoteType;

import java.time.LocalDateTime;

public class PaymentResponseDto {

    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class TradeLogDto{
        private Integer point;
        private NoteType log;
        private LocalDateTime createdAt;
    }
}
