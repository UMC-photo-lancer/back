package shop.photolancer.photolancer.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.photolancer.photolancer.exception.ResponseMessage;
import shop.photolancer.photolancer.exception.StatusCode;
import shop.photolancer.photolancer.service.impl.FollowServiceImpl;
import shop.photolancer.photolancer.web.dto.FollowingRequestDto;
import shop.photolancer.photolancer.web.dto.FollowingResponseDto;
import shop.photolancer.photolancer.web.dto.PostResponseDto;
import shop.photolancer.photolancer.web.dto.base.DefaultRes;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/following")
public class FollowingController {
    // 팔로잉 요청
    public final FollowServiceImpl followService;
    @PostMapping("/request")
    public ResponseEntity requestFollow(@RequestBody FollowingRequestDto.RequestFollowDto followingUserName) {
        try {
            followService.requestFollow(followingUserName.getFollowingUserName(), 1L);

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
            return followService.followingUsersPosts(pageable);
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }
}
