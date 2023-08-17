package shop.photolancer.photolancer.web.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.photolancer.photolancer.domain.User;
import shop.photolancer.photolancer.exception.ResponseMessage;
import shop.photolancer.photolancer.exception.StatusCode;
import shop.photolancer.photolancer.service.impl.ProfileServiceImpl;
import shop.photolancer.photolancer.service.impl.UserServiceImpl;
import shop.photolancer.photolancer.web.controller.base.BaseController;
import shop.photolancer.photolancer.web.dto.PostResponseDto;
import shop.photolancer.photolancer.web.dto.base.DefaultRes;

@RestController
@RequiredArgsConstructor
@RequestMapping("/profile")
public class ProfileController extends BaseController {
    private final UserServiceImpl userService;
    private final ProfileServiceImpl profileService;
    @GetMapping("/{userNickname}")
    public ResponseEntity getUserProfile(@PathVariable String userNickname, @PageableDefault(size = 12, sort = "createdAt", direction = Sort.Direction.DESC)
    Pageable request) {
        try {
            User currentUser = userService.getCurrentUser();
            User user = userService.findUserByNickName(userNickname);
            Page<PostResponseDto.PostListDto> response =  profileService.userPosts(request, currentUser, user);


            return new ResponseEntity( DefaultRes.res(StatusCode.OK, ResponseMessage.GET_USER_PROFILE_POST_SUCCESS, response), HttpStatus.OK);
        } catch (Exception e) {
            return handleApiException(e, HttpStatus.BAD_REQUEST);
        }
    }
}
