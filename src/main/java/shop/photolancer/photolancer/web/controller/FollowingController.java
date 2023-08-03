package shop.photolancer.photolancer.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.photolancer.photolancer.exception.ResponseMessage;
import shop.photolancer.photolancer.exception.StatusCode;
import shop.photolancer.photolancer.service.impl.FollowServiceImpl;
import shop.photolancer.photolancer.web.dto.FollowRequestDto;
import shop.photolancer.photolancer.web.dto.base.DefaultRes;

@RestController
@RequiredArgsConstructor
@RequestMapping("/follow")
public class FollowingController {
    // 팔로잉 요청
    public final FollowServiceImpl followService;
    @PostMapping("/request")
    public ResponseEntity requestFollow(@RequestBody FollowRequestDto.RequestFollowDto followingUserName) {
        try {
            followService.requestFollow(followingUserName.getFollowingUserName(), 1L);

            return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.FOLLOW_REQUEST_SUCCESS), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.BAD_REQUEST);
        }
    }
}
