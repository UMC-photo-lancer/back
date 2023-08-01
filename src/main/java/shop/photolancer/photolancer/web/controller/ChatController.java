package shop.photolancer.photolancer.web.controller;

import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;
import shop.photolancer.photolancer.domain.ChatRoom;
import shop.photolancer.photolancer.domain.Message;
import shop.photolancer.photolancer.exception.ResponseMessage;
import shop.photolancer.photolancer.exception.StatusCode;
import shop.photolancer.photolancer.mapper.ChatMapper;
import shop.photolancer.photolancer.service.ChatService;
import shop.photolancer.photolancer.web.dto.ChatResponseDto;
import shop.photolancer.photolancer.web.dto.MessageDto;
import shop.photolancer.photolancer.web.dto.base.DefaultRes;

import java.util.List;

@Api(tags = "채팅 관련 API")
@RestController
@Slf4j
@Log4j2
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;
    private final ChatMapper mapper;

    @GetMapping("/chat")
    public String chatGET(){

        log.info("@ChatController, chat GET()");

        return "chat";
    }

    @ApiOperation(value = "팔로잉 채팅 목록 불러오기 API")
    @ApiImplicitParam(name = "last", value = "마지막으로 본 페이지", required = true, dataType = "Long", example = "1", paramType = "query")
    @ApiResponse(code = 200, message = "팔로잉 채팅 목록 불러오기 성공")
    @GetMapping("/following")
    public ResponseEntity getFollowingChats(@RequestParam(defaultValue = "1") Long last){
        try {
            //추후 유저 인증 구현
            //
            Long userId = Long.valueOf(1);
            //
            Page<ChatRoom> chatPage = chatService.findFollowingChats(userId, last);

            List<ChatRoom> chats = chatPage.getContent();
            List<ChatResponseDto.ChatResponse> responses = mapper.chatListTochatResponseDtos(chats);

            return new ResponseEntity( DefaultRes.res(StatusCode.OK, ResponseMessage.FOLLOWING_CHATS_READ_SUCCESS, responses), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "채팅 내용 불러오기 API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "chat-id", value = "채팅방 ID", required = true, dataType = "Long", example = "5", paramType = "path"),
            @ApiImplicitParam(name = "last", value = "마지막으로 본 페이지", required = true, dataType = "Long", example = "1", paramType = "query")
    })
    @ApiResponse(code = 200, message = "채팅 내용 불러오기 성공")
    @GetMapping("/{chat-id}")
    public ResponseEntity getChatMessages(@PathVariable("chat-id") Long chatId,
                                          @RequestParam(defaultValue = "1") Long last){
        try {
            //추후 유저 인증 구현
            //
            Long userId = Long.valueOf(1);
            //
            Page<Message> messages = chatService.findMessages(chatId, last);

            List<Message> messageList = messages.getContent();
            List<ChatResponseDto.MessageResponse> responses = mapper.messagesToMessageResponseDtos(messageList);

            return new ResponseEntity( DefaultRes.res(StatusCode.OK, ResponseMessage.MESSAGE_READ_SUCCESS, responses), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }

}
