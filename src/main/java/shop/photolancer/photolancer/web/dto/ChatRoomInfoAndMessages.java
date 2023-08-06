package shop.photolancer.photolancer.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class ChatRoomInfoAndMessages {
    private UserResponseDto.ChatUserDto userInfo;
    private List<ChatResponseDto.MessageResponse> messages;

    public ChatRoomInfoAndMessages(UserResponseDto.ChatUserDto userInfo, List<ChatResponseDto.MessageResponse> messages) {
        this.userInfo = userInfo;
        this.messages = messages;
    }

    // getter, setter 생략
}
