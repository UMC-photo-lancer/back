package shop.photolancer.photolancer.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import shop.photolancer.photolancer.domain.ChatRoom;
import shop.photolancer.photolancer.domain.Message;
import shop.photolancer.photolancer.domain.User;
import shop.photolancer.photolancer.repository.ChatRoomRepository;
import shop.photolancer.photolancer.repository.UserRepository;
import shop.photolancer.photolancer.web.dto.MessageDto;

@RequiredArgsConstructor
@Component
public class ChatConverter {
    private final UserRepository userRepository;
    private final ChatRoomRepository chatRoomRepository;

    public MessageDto toDTO(Message message) {
        return MessageDto.builder()
                .id(message.getId())
                .chatId(message.getChat().getRoomid())
                .senderId(message.getSender().getId())
                .content(message.getContent())
                .build();
    }

    public Message toEntity(MessageDto dto) {
        Message message = Message.builder()
                .id(dto.getId())
                .content(dto.getContent())
                .build();

        // 데이터베이스에서 ChatRoom과 User 객체를 조회하여 설정합니다.
        ChatRoom chatRoom = chatRoomRepository.findById(dto.getChatId()).orElse(null);
        User sender = userRepository.findById(dto.getSenderId()).orElse(null);

        message.setChat(chatRoom);
        message.setSender(sender);

        return message;
    }
}
