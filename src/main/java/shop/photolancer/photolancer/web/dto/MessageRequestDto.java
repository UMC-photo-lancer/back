package shop.photolancer.photolancer.web.dto;


import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MessageRequestDto {
    @ApiModelProperty(example = "1")
    @ApiParam(name = "roomId", value = "채팅방 ID", required = true)
    Long roomId;

    @ApiModelProperty(example = "message content")
    @ApiParam(name = "content", value = "메시지 내용", required = true)
    String content;
}
