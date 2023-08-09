package shop.photolancer.photolancer.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.photolancer.photolancer.domain.User;
import shop.photolancer.photolancer.service.impl.FollowServiceImpl;
import shop.photolancer.photolancer.service.impl.UserServiceImpl;
import shop.photolancer.photolancer.web.dto.UserResponseDto;

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
}
