package shop.photolancer.photolancer.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.photolancer.photolancer.converter.ChatConverter;
import shop.photolancer.photolancer.domain.ChatRoom;
import shop.photolancer.photolancer.domain.Message;
import shop.photolancer.photolancer.domain.User;
import shop.photolancer.photolancer.exception.CustomExceptions;
import shop.photolancer.photolancer.exception.ResponseMessage;
import shop.photolancer.photolancer.exception.StatusCode;
import shop.photolancer.photolancer.mapper.ChatMapper;
import shop.photolancer.photolancer.repository.UserRepository;
import shop.photolancer.photolancer.service.ChatService;
import shop.photolancer.photolancer.service.impl.UserServiceImpl;
import shop.photolancer.photolancer.web.controller.base.BaseController;
import shop.photolancer.photolancer.web.dto.ChatRequestDto;
import shop.photolancer.photolancer.web.dto.ChatResponseDto;
import shop.photolancer.photolancer.web.dto.ChatRoomInfoAndMessages;
import shop.photolancer.photolancer.web.dto.UserResponseDto;
import shop.photolancer.photolancer.web.dto.base.DefaultRes;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Api(tags = "채팅 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatRoomController extends BaseController {

    private final UserRepository userRepository;
    private final ChatService chatService;
    private final ChatMapper mapper;
    private final UserServiceImpl userService;
    private final ChatConverter chatConverter;

    @ApiOperation(value = "채팅 목록 불러오기 API")
    @ApiResponse(code = 200, message = "채팅 목록 불러오기 성공")
    @ApiImplicitParam(name = "page", value = "페이지 번호", dataType = "int", example = "1", paramType = "query")
    @GetMapping("/rooms")
    public ResponseEntity getAllChatRooms(@RequestParam(defaultValue = "1") Long last) {
        try {
            logger.info("Received request: method={}, path={}, description={}", "GET", "/chat/rooms", "채팅 목록 불러오기 API");

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
        } catch (CustomExceptions.GetAllChatRoomsException e) {
            return handleApiException(e, HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "채팅방 생성 API")
    @ApiImplicitParam(name = "receiver-id", value = "채팅 상대 ID", required = true, dataType = "Long", example = "1", paramType = "path")
    @ApiResponse(code = 200, message = "채팅방 생성 성공")
    @PostMapping("/room/{receiver-id}")
    @ResponseBody
    public ResponseEntity createRoom(@PathVariable("receiver-id") Long receiverId) {
        try {
            logger.info("Received request: method={}, path={}, description={}", "POST", "/chat/room/{receiver-id}", "채팅방 생성 API");

            User user = userService.getCurrentUser();

            ChatRoom chatRoom = chatService.createRoom(user, receiverId);

            Long response = chatRoom.getId();

            return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.CREATE_CHAT_ROOM_SUCCESS, response), HttpStatus.OK);
        } catch (CustomExceptions.CreateRoomException e) {
            return handleApiException(e, HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "채팅방 불러오기 API")
    @ApiImplicitParam(name = "chat-id", value = "채팅방 ID", required = true, dataType = "Long", example = "1", paramType = "path")
    @ApiResponse(code = 200, message = "채팅방 불러오기 성공")
    @GetMapping("/room/{chat-id}")
    public ResponseEntity getChatMessages(@PathVariable("chat-id") Long chatId,
                                          @RequestParam(defaultValue = "1") Long last, @RequestBody ChatRequestDto request) {
        try {
            logger.info("Received request: method={}, path={}, description={}", "GET", "/chat/room/{chat-id}", "채팅방 불러오기 API");

            User user = userService.getCurrentUser();

            Long otherUserId = request.getOtherUserId();

            User otherUser = userRepository.findById(otherUserId).orElseThrow(() -> new NoSuchElementException("User not found."));

            Page<Message> messages = chatService.findMessages(chatId, last);

            List<Message> messageList = messages.getContent();

            UserResponseDto.ChatUserDto userInfo = chatConverter.toUserInfo(otherUser);

            List<ChatResponseDto.MessageResponse> responses = mapper.messagesToMessageResponseDtos(messageList);

            ChatRoomInfoAndMessages result = new ChatRoomInfoAndMessages(userInfo, responses);

            return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.MESSAGE_READ_SUCCESS, result), HttpStatus.OK);
        } catch (CustomExceptions.GetChatMessagesException e) {
            return handleApiException(e, HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "채팅방 검색 API")
    @ApiResponse(code = 200, message = "채팅방 검색 성공")
    @ApiImplicitParam(name = "nickname", value = "검색하려는 유저 닉네임", dataType = "string", example = "오리난쟁", paramType = "query")
    @PostMapping("/search")
    public ResponseEntity searchRoom(@RequestParam String nickname) {
        try {
            logger.info("Received request: method={}, path={}, description={}", "POST", "/chat/search", "채팅방 검색 API");

            User user = userService.getCurrentUser();

            Long userId = user.getId();

            ChatRoom chatRoom = chatService.searchRoom(user, nickname);

            //상대방 정보 가져오기
            User receiver = chatRoom.getReceiver().getId().equals(userId) ? chatRoom.getSender() : chatRoom.getReceiver();

            UserResponseDto.ChatUserDto response = new UserResponseDto.ChatUserDto(
                    receiver.getId(),
                    receiver.getLevel(),
                    receiver.getNickname(),
                    receiver.getProfileUrl());


            return new ResponseEntity( DefaultRes.res(StatusCode.OK, ResponseMessage.SEARCH_CHAT_ROOM_SUCCESS, response), HttpStatus.OK);
        } catch (CustomExceptions.SearchRoomException e) {
            return handleApiException(e, HttpStatus.BAD_REQUEST);
        }
    }

}
