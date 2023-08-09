package shop.photolancer.photolancer.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.photolancer.photolancer.domain.User;
import shop.photolancer.photolancer.exception.ResponseMessage;
import shop.photolancer.photolancer.exception.StatusCode;
import shop.photolancer.photolancer.service.impl.FollowServiceImpl;
import shop.photolancer.photolancer.service.impl.UserServiceImpl;
import shop.photolancer.photolancer.web.dto.FollowingRequestDto;
import shop.photolancer.photolancer.web.dto.FollowingResponseDto;
import shop.photolancer.photolancer.web.dto.UserResponseDto;
import shop.photolancer.photolancer.web.dto.base.DefaultRes;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/following")
public class FollowingController {
    // 팔로잉 요청
    public final FollowServiceImpl followService;
    public final UserServiceImpl userService;
    @PostMapping("/request")
    public ResponseEntity requestFollow(@RequestBody FollowingRequestDto.RequestFollowDto followingUserId) {
        try {
            User user = userService.getCurrentUser();
            followService.requestFollow(followingUserId.getFollowingUserId(), user.getId());

            return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.FOLLOW_REQUEST_SUCCESS), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.BAD_REQUEST);
        }
    }

    // 팔로잉 하는 유저의 게시글 모아보기
    @GetMapping("/posts")
    public Page<FollowingResponseDto.FollowingUserPostsDto> followingUsersPosts(
            @PageableDefault(size = 12) Pageable pageable) {
        try {
            User user = userService.getCurrentUser();
            return followService.followingUsersPosts(pageable, user);
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    // 팔로잉 유저 모아보기
    @GetMapping("/users")
    public List<UserResponseDto.PostUserDto> followingUsers (){
        try {
            User user = userService.getCurrentUser();
            return followService.followingUsers(user);
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }
}
