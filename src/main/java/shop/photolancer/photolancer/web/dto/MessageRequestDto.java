package shop.photolancer.photolancer.web.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MessageRequestDto {
    Long roomId;
    String content;
}
