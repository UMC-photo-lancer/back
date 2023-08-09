package shop.photolancer.photolancer.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.photolancer.photolancer.domain.User;
import shop.photolancer.photolancer.exception.ResponseMessage;
import shop.photolancer.photolancer.exception.StatusCode;
import shop.photolancer.photolancer.service.impl.FollowServiceImpl;
import shop.photolancer.photolancer.service.impl.UserServiceImpl;
import shop.photolancer.photolancer.web.dto.FollowRequestDto;
import shop.photolancer.photolancer.web.dto.UserResponseDto;
import shop.photolancer.photolancer.web.dto.base.DefaultRes;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/follower")
public class FollowerController {
    public final FollowServiceImpl followService;
    public final UserServiceImpl userService;

    @GetMapping("/users")
    public List<UserResponseDto.FollowerUserDto> followerUsers (){
        try {
            User user = userService.getCurrentUser();
            return followService.followerUsers(user);
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    @DeleteMapping
    public ResponseEntity deleteFollower(@RequestBody FollowRequestDto.DeleteFollowerDto userId) {
        try {
            User user = userService.getCurrentUser();
            System.out.println(user.getId());
            System.out.println(userId.getFollowerUserId());
            followService.deleteFollower(userId.getFollowerUserId(), user);
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.FOLLOWER_DELETE_SUCCESS), HttpStatus.OK);
    }
}
