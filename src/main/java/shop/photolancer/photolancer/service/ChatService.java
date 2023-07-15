package shop.photolancer.photolancer.service;

import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import shop.photolancer.photolancer.domain.Chat;
import shop.photolancer.photolancer.domain.Message;
import shop.photolancer.photolancer.domain.User;

public interface ChatService {

    Page<Chat> findFollowingChats(Long userId, Long last);

    Page<Message> findMessages(Long chatId, Long last);

}
