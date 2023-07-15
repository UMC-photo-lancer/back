package shop.photolancer.photolancer.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.photolancer.photolancer.domain.Chat;
import shop.photolancer.photolancer.domain.User;
import shop.photolancer.photolancer.repository.ChatRepository;
import shop.photolancer.photolancer.repository.UserRepository;
import shop.photolancer.photolancer.service.ChatService;

import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;

    private final UserRepository userRepository;

    //팔로잉 채팅 목록 불러오기
    @Transactional
    @Override
    public Page<Chat> findFollowingChats(Long userId, Long last){
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
        Page<Chat> chatRooms = chatRepository.findAllBySenderOrReceiver(pageable, user, user);

        return chatRooms;
    }

}
