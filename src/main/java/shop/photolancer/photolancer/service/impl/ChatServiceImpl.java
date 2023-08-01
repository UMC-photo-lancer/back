package shop.photolancer.photolancer.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.photolancer.photolancer.converter.ChatConverter;
import shop.photolancer.photolancer.domain.ChatRoom;
import shop.photolancer.photolancer.domain.Message;
import shop.photolancer.photolancer.domain.User;
import shop.photolancer.photolancer.repository.ChatRepository;
import shop.photolancer.photolancer.repository.MessageRepository;
import shop.photolancer.photolancer.repository.UserRepository;
import shop.photolancer.photolancer.service.ChatService;
import shop.photolancer.photolancer.web.dto.MessageDto;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;
    private final UserRepository userRepository;
    private final MessageRepository messageRepository;
    private final ChatConverter chatConverter;

    //팔로잉 채팅 목록 불러오기
    @Override
    public Page<ChatRoom> findFollowingChats(Long userId, Long last){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        Pageable pageable;

        if (last != null && last > 1){
            // last 값이 1보다 크고 있을 경우, 해당 값 기준으로 다음 페이지를 요청
            pageable = PageRequest.of(last.intValue()-1, 5);
        } else {
            // last 값이 1 이하일 경우, 첫 번째 페이지 요청
            pageable = PageRequest.of(0, 5);
        }
        Page<ChatRoom> chatRooms = chatRepository.findAllBySenderOrReceiver(pageable, user, user);

        return chatRooms;
    }

    @Override
    public Page<Message> findMessages(Long chatId, Long last){
        ChatRoom chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new NoSuchElementException("Chat not found"));

        Pageable pageable;

        if (last != null && last > 1){
            // last 값이 1보다 크고 있을 경우, 해당 값 기준으로 다음 페이지를 요청
            pageable = PageRequest.of(last.intValue()-1, 5);
        } else {
            // last 값이 1 이하일 경우, 첫 번째 페이지 요청
            pageable = PageRequest.of(0, 5);
        }

        Page<Message> messages = messageRepository.findByChat(pageable, chat);

        return messages;

    }


    private SimpMessagingTemplate messagingTemplate;
    private List<Message> chatHistory;

    @Override
    @Transactional
    public void processMessage(Message message) {
        chatHistory.add(message);
        messagingTemplate.convertAndSend("/sub/chats", message);
    }

    @Override
    public List<Message> getChatHistory() {
        return chatHistory;
    }

    @Override
    @Transactional
    public MessageDto saveMessage(MessageDto messageDTO) {
        // DTO를 Entity로 변환
        Message message = chatConverter.toEntity(messageDTO);

        // Entity를 저장
        Message savedMessage = messageRepository.save(message);

        // 저장된 Entity를 다시 DTO로 변환하여 반환
        return chatConverter.toDTO(savedMessage);
    }

}
