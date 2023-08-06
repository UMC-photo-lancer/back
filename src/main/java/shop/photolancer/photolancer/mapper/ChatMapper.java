package shop.photolancer.photolancer.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import shop.photolancer.photolancer.domain.ChatRoom;
import shop.photolancer.photolancer.domain.Message;
import shop.photolancer.photolancer.web.dto.ChatResponseDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ChatMapper {

    ChatResponseDto.ChatResponse chatTochatResponseDto(ChatRoom chat);

    @Mapping(target = "senderId", source = "sender.id") // 발신자의 ID를 User 엔티티의 ID로 매핑
    ChatResponseDto.MessageResponse messageToMessageResponseDto(Message message);

    List<ChatResponseDto.MessageResponse> messagesToMessageResponseDtos(List<Message> messages);

}
