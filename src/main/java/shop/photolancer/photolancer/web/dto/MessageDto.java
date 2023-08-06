package shop.photolancer.photolancer.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter @Builder
public class MessageDto {
    private Long id;
    private Long chatId;
    private Long senderId;
    private String content;
}