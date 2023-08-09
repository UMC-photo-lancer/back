package shop.photolancer.photolancer.web.dto;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Getter;

@Getter
public class ChatRequestDto {
    @ApiModelProperty(example = "5")
    @ApiParam(name = "otherUserId", value = "채팅 상대 아이디", required = true)
    private Long otherUserId;
}
