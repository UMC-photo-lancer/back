package shop.photolancer.photolancer.mapper;

import org.mapstruct.Mapper;
import shop.photolancer.photolancer.domain.Chat;
import shop.photolancer.photolancer.domain.Message;
import shop.photolancer.photolancer.web.dto.ChatResponseDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ChatMapper {
    List<ChatResponseDto.ChatResponse> chatListTochatResponseDtos(List<Chat> chats);

    List<ChatResponseDto.MessageResponse> messagesToMessageResponseDtos(List<Message> messages);

}
