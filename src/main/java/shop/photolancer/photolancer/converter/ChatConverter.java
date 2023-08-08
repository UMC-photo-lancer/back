package shop.photolancer.photolancer.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import shop.photolancer.photolancer.domain.ChatRoom;
import shop.photolancer.photolancer.domain.Message;
import shop.photolancer.photolancer.domain.User;
import shop.photolancer.photolancer.web.dto.UserResponseDto;

@RequiredArgsConstructor
@Component
public class ChatConverter {

    public ChatRoom toChatRoom(User sender, User receiver){
        return ChatRoom.builder()
                .sender(sender)
                .receiver(receiver)
                .build();
    }

    public Message toMessage(ChatRoom chatRoom, User sender, String content){
        return Message.builder()
                .chatRoom(chatRoom)
                .sender(sender)
                .content(content)
                .build();
    }

    public UserResponseDto.ChatUserDto toUserInfo(User otherUser){
        return UserResponseDto.ChatUserDto.builder()
                .id(otherUser.getId())
                .level(otherUser.getLevel())
                .nickname(otherUser.getNickname())
                .profileUrl(otherUser.getProfileUrl())
                .build();
    }
}
