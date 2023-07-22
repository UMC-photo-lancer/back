package shop.photolancer.photolancer.web.dto;

import lombok.*;
import shop.photolancer.photolancer.domain.Account;
import shop.photolancer.photolancer.domain.enums.NoteType;

import java.time.LocalDateTime;
import java.util.List;

public class PaymentResponseDto {

    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class TradeLogDto{
        private Integer point;
        private NoteType log;
        private String createdAt;
    }

    @Builder @Getter
    @Data
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class ExchangeDto{
        private Long id;
        private String bank;
        private String accountNumber;
        private Boolean isMain;
    }

    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class PurchaseDto{
        private Integer price;
        private Integer userPoint;
    }

}
