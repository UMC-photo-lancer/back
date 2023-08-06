package shop.photolancer.photolancer.service;

import org.springframework.data.domain.Page;
import shop.photolancer.photolancer.domain.ChatRoom;
import shop.photolancer.photolancer.domain.Message;
import shop.photolancer.photolancer.domain.User;

public interface ChatService {
    Page<ChatRoom> findAllChats(Long userId, Long last);
    ChatRoom createRoom(User user, Long receiverId);
    Page<Message> findMessages(Long chatId, Long last);
    Message findLastMessageByChatRoomId(Long chatRoomId);
    ChatRoom searchRoom(User user, String nickname);
}
