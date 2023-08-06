package shop.photolancer.photolancer.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import shop.photolancer.photolancer.domain.ChatRoom;
import shop.photolancer.photolancer.domain.Message;
import shop.photolancer.photolancer.domain.User;
import shop.photolancer.photolancer.exception.ResponseMessage;
import shop.photolancer.photolancer.exception.StatusCode;
import shop.photolancer.photolancer.mapper.ChatMapper;
import shop.photolancer.photolancer.repository.UserRepository;
import shop.photolancer.photolancer.service.ChatService;
import shop.photolancer.photolancer.service.impl.UserServiceImpl;
import shop.photolancer.photolancer.web.dto.ChatRequestDto;
import shop.photolancer.photolancer.web.dto.ChatResponseDto;
import shop.photolancer.photolancer.web.dto.ChatRoomInfoAndMessages;
import shop.photolancer.photolancer.web.dto.UserResponseDto;
import shop.photolancer.photolancer.web.dto.base.DefaultRes;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatRoomController {

    private final UserRepository userRepository;
    private final ChatService chatService;
    private final ChatMapper mapper;
    private final UserServiceImpl userService;

    //채팅방 목록 반환
    @GetMapping("/rooms")
    public ResponseEntity getAllChatRooms(@RequestParam(defaultValue = "1") Long last) {
        try {
            User user = userService.getCurrentUser();
            Long userId = user.getId();

            Page<ChatRoom> chatPage = chatService.findAllChats(userId, last);

            List<ChatRoom> chats = chatPage.getContent();
            List<ChatResponseDto.ChatResponse> responses = new ArrayList<>();

            for (ChatRoom chat : chats) {
                // 현재 사용자가 보낸 채팅인 경우 상대방 정보를 가져옴
                User receiver = chat.getReceiver().getId().equals(userId) ? chat.getSender() : chat.getReceiver();

                // 채팅방에 해당하는 가장 최근 메시지 정보를 가져옴
                Message lastMessage = chatService.findLastMessageByChatRoomId(chat.getId());

                ChatResponseDto.ChatResponse response = mapper.chatTochatResponseDto(chat);
                response.getSender().setId(receiver.getId());
                response.getSender().setLevel(receiver.getLevel());
                response.getSender().setNickname(receiver.getNickname());
                response.getSender().setProfileUrl(receiver.getProfileUrl());

                // 채팅방에 해당하는 최근 메시지 정보를 설정
                if (lastMessage != null) {
                    ChatResponseDto.MessageResponse messageResponse = new ChatResponseDto.MessageResponse(
                            lastMessage.getId(),
                            lastMessage.getSender().getId(),
                            lastMessage.getContent(),
                            lastMessage.getCreatedAt()
                    );
                    response.setLastMessage(messageResponse);
                }
                responses.add(response);
            }

            return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.CHATS_READ_SUCCESS, responses), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }

    // 채팅방 생성
    @PostMapping("/room/{receiver-id}")
    @ResponseBody
    public ResponseEntity createRoom(@PathVariable("receiver-id") Long receiverId) {
        try {
            User user = userService.getCurrentUser();

            ChatRoom chatRoom = chatService.createRoom(user, receiverId);

            Long response = chatRoom.getId();

            return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.CREATE_CHAT_ROOM_SUCCESS, response), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }


    // 특정 채팅방 조회
    @GetMapping("/room/{chat-id}")
    public ResponseEntity getChatMessages(@PathVariable("chat-id") Long chatId,
                                          @RequestParam(defaultValue = "1") Long last, @RequestBody ChatRequestDto request) {
        try {
            User user = userService.getCurrentUser();

            Long otherUserId = request.getOtherUserId();

            User otherUser = userRepository.findById(otherUserId).orElseThrow(() -> new NoSuchElementException("User not found."));

            Page<Message> messages = chatService.findMessages(chatId, last);

            List<Message> messageList = messages.getContent();

            UserResponseDto.ChatUserDto userInfo = UserResponseDto.ChatUserDto.builder()
                    .id(otherUserId)
                    .level(otherUser.getLevel())
                    .nickname(otherUser.getNickname())
                    .profileUrl(otherUser.getProfileUrl())
                    .build();

            List<ChatResponseDto.MessageResponse> responses = mapper.messagesToMessageResponseDtos(messageList);

            ChatRoomInfoAndMessages result = new ChatRoomInfoAndMessages(userInfo, responses);

            return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.MESSAGE_READ_SUCCESS, result), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }

    //채팅 검색
    @PostMapping("/search")
    @ResponseBody
    public ResponseEntity searchRoom(@RequestParam String nickname) {
        try {
            User user = userService.getCurrentUser();

            Long userId = user.getId();

            ChatRoom chatRoom = chatService.searchRoom(user, nickname);

            // 상대방 정보를 가져옴
            User receiver = chatRoom.getReceiver().getId().equals(userId) ? chatRoom.getSender() : chatRoom.getReceiver();

            UserResponseDto.ChatUserDto response = new UserResponseDto.ChatUserDto(
                    receiver.getId(),
                    receiver.getLevel(),
                    receiver.getNickname(),
                    receiver.getProfileUrl());


            return new ResponseEntity( DefaultRes.res(StatusCode.OK, ResponseMessage.SEARCH_CHAT_ROOM_SUCCESS, response), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }

}
