package shop.photolancer.photolancer.service;

import org.springframework.data.domain.Page;
import shop.photolancer.photolancer.domain.ChatRoom;
import shop.photolancer.photolancer.domain.Message;
import shop.photolancer.photolancer.web.dto.MessageDto;

import java.util.List;

public interface ChatService {

    Page<ChatRoom> findFollowingChats(Long userId, Long last);

    Page<Message> findMessages(Long chatId, Long last);

    void processMessage(Message message);

    List<Message> getChatHistory();

    MessageDto saveMessage(MessageDto messageDTO);

}
