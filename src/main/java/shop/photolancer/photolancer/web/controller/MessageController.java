package shop.photolancer.photolancer.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.photolancer.photolancer.domain.ChatRoom;
import shop.photolancer.photolancer.domain.Message;
import shop.photolancer.photolancer.domain.User;
import shop.photolancer.photolancer.exception.ResponseMessage;
import shop.photolancer.photolancer.exception.StatusCode;
import shop.photolancer.photolancer.repository.ChatRoomRepository;
import shop.photolancer.photolancer.service.ChatService;
import shop.photolancer.photolancer.service.impl.UserServiceImpl;
import shop.photolancer.photolancer.web.dto.MessageRequestDto;
import shop.photolancer.photolancer.web.dto.base.DefaultRes;

import java.util.NoSuchElementException;

@Api(tags = "메시지 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
public class MessageController {
    private final ChatService chatService;
    private final UserServiceImpl userService;
    private final ChatRoomRepository chatRoomRepository;

    private final SimpMessageSendingOperations sendingOperations;

    @MessageMapping("/message")
    public void enter(Message message) {
        sendingOperations.convertAndSend("/topic/chat/room/"+message.getChatRoom(),message);
    }

    @ApiOperation(value = "메시지 저장 API")
    @ApiResponse(code = 200, message = "메시지 저장 성공")
    @PostMapping("/message")
    public ResponseEntity saveMessage(@RequestBody MessageRequestDto request){
        try {
            User user = userService.getCurrentUser();

            ChatRoom chatRoom = chatRoomRepository.findById(request.getRoomId()).orElseThrow(() -> new NoSuchElementException("ChatRoom not found."));
            User sender = user; // 현재 사용자의 닉네임으로 설정
            String content = request.getContent();

            // 메시지 저장
            chatService.saveMessage(chatRoom, sender, content);

            return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.MESSAGE_SAVE_SUCCESS), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }
}