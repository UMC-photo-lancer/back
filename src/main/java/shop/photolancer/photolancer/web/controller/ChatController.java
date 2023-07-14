package shop.photolancer.photolancer.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import shop.photolancer.photolancer.domain.Chat;
import shop.photolancer.photolancer.exception.ResponseMessage;
import shop.photolancer.photolancer.exception.StatusCode;
import shop.photolancer.photolancer.mapper.ChatMapper;
import shop.photolancer.photolancer.service.ChatService;
import shop.photolancer.photolancer.web.dto.ChatResponseDto;
import shop.photolancer.photolancer.web.dto.TestResponseDto;
import shop.photolancer.photolancer.web.dto.base.DefaultRes;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/chats")
public class ChatController {

    private final ChatService chatService;

    private final ChatMapper mapper;

    //팔로잉 채팅 목록 불러오기
    @GetMapping("/following")
    public ResponseEntity getFollowingChats(@RequestParam(defaultValue = "1") Long last){
        try {
            //추후 유저 인증 구현
            //
            Long userId = Long.valueOf(1);
            //
            Page<Chat> chatPage = chatService.findFollowingChats(userId, last);

            List<Chat> chats = chatPage.getContent();
            List<ChatResponseDto.ChatResponse> responses = mapper.chatListTochatResponseDtos(chats);

            return new ResponseEntity( DefaultRes.res(StatusCode.OK, ResponseMessage.FOLLOWING_CHATS_READ_SUCCESS, responses), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }

}
