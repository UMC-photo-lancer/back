package shop.photolancer.photolancer.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.photolancer.photolancer.domain.ChatRoom;
import shop.photolancer.photolancer.domain.Message;
import shop.photolancer.photolancer.domain.User;
import shop.photolancer.photolancer.mapper.ChatMapper;
import shop.photolancer.photolancer.repository.ChatRoomRepository;
import shop.photolancer.photolancer.repository.MessageRepository;
import shop.photolancer.photolancer.repository.UserRepository;
import shop.photolancer.photolancer.service.ChatService;

import javax.annotation.PostConstruct;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {
    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;
    private final MessageRepository messageRepository;

    private final ChatMapper mapper;
    private Map<Long, ChatRoom> chatRooms;

    @PostConstruct
    //의존관게 주입완료되면 실행되는 코드
    private void init() {
        chatRooms = new LinkedHashMap<>();
    }

    @Override
    public Page<ChatRoom> findAllChats(Long userId, Long last){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        Pageable pageable;

        if (last != null && last > 1){
            // last 값이 1보다 크고 있을 경우, 해당 값 기준으로 다음 페이지를 요청
            pageable = PageRequest.of(last.intValue(), 5);
        } else {
            // last 값이 1 이하일 경우, 첫 번째 페이지 요청
            pageable = PageRequest.of(0, 5);
        }
        Page<ChatRoom> chatRooms = chatRoomRepository.findAllBySenderOrReceiver(pageable, user, user);

        return chatRooms;
    }

    @Transactional
    @Override
    public ChatRoom createRoom(User user, Long receiverId) {
        User receiver = userRepository.findById(receiverId).orElseThrow(()->new NoSuchElementException("User not found."));

        // 이미 채팅방이 존재하는지 확인
        ChatRoom existingChatRoom = chatRoomRepository.findBySenderAndReceiver(user, receiver);
        ChatRoom existingChatRoom2 = chatRoomRepository.findBySenderAndReceiver(receiver, user);
        if (existingChatRoom != null || existingChatRoom2 != null) {
            return existingChatRoom;
        }

        //없으면 생성
        ChatRoom chatRoom = ChatRoom.builder()
                .sender(user)
                .receiver(receiver)
                .build();
        chatRoomRepository.save(chatRoom);

        return chatRoom;
    }

    @Override
    public Page<Message> findMessages(Long chatId, Long last){
        ChatRoom chat = chatRoomRepository.findById(chatId)
                .orElseThrow(() -> new NoSuchElementException("Chat not found"));

        Pageable pageable;

        if (last != null && last > 1){
            // last 값이 1보다 크고 있을 경우, 해당 값 기준으로 다음 페이지를 요청
            pageable = PageRequest.of(last.intValue()-1, 5);
        } else {
            // last 값이 1 이하일 경우, 첫 번째 페이지 요청
            pageable = PageRequest.of(0, 5);
        }

        Page<Message> messages = messageRepository.findByChatRoom(pageable, chat);

        return messages;
    }

    @Transactional
    @Override
    public Message findLastMessageByChatRoomId(Long chatRoomId) {
        return messageRepository.findTopByChatRoomIdOrderByCreatedAtDesc(chatRoomId);
    }

    @Transactional
    @Override
    public ChatRoom searchRoom(User user, String nickname){
        User otherUser = userRepository.findByNickname(nickname);

        ChatRoom chatRoom = chatRoomRepository.findBySenderOrReceiver(user, otherUser);

        return chatRoom;
    }

    @Transactional
    @Override
    public void saveMessage(ChatRoom chatRoom, User sender, String content){
        Message newMessage = Message.builder()
                .chatRoom(chatRoom)
                .sender(sender)
                .content(content)
                .build();

        messageRepository.save(newMessage);
    }
}
