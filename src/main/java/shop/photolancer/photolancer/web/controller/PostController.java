package shop.photolancer.photolancer.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import shop.photolancer.photolancer.domain.User;
import shop.photolancer.photolancer.exception.ResponseMessage;
import shop.photolancer.photolancer.exception.StatusCode;
import shop.photolancer.photolancer.service.impl.PostImageUploadService;
import shop.photolancer.photolancer.service.impl.PostServiceImpl;
import shop.photolancer.photolancer.service.impl.UserServiceImpl;
import shop.photolancer.photolancer.web.dto.PostRequestDto;
import shop.photolancer.photolancer.web.dto.PostResponseDto;
import shop.photolancer.photolancer.web.dto.base.DefaultRes;


import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {
    private final PostServiceImpl postService;
    private final PostImageUploadService postImageUploadService;
    private final UserServiceImpl userService;
    @PostMapping
    public ResponseEntity uploadPost(@RequestPart("postContent") PostRequestDto.PostUploadDto request,
                                     @RequestPart("imgFile") List<MultipartFile> multipartFiles) {
        try {

            List<String> imgPaths = postImageUploadService.upload(multipartFiles);
            String content = request.getContent();
            Integer likeCount = request.getLikeCount();
            Boolean isSale = request.getIsSale();
            Integer point = request.getPoint();
            List<String> bookmarkList = request.getBookmark();
            User user = userService.getCurrentUser();
            postService.upload(content, likeCount, isSale, point, imgPaths, bookmarkList, user);

            return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.POST_UPLOAD_SUCCESS), HttpStatus.OK);
        } catch (Exception e){
        return new ResponseEntity(e, HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/{id}")
    public PostResponseDto.PostDetailDto postDetail(@PathVariable Long id) {
        User user = userService.getCurrentUser();
        return postService.searchById(id, user);
    }

    @PutMapping("/{id}/like")
    public ResponseEntity updateLike(@PathVariable Long id) {
        try {
            User user = userService.getCurrentUser();
            postService.updateLike(id, user);

            return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.POST_LIKE_SUCCESS), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity(e, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletePost(@PathVariable Long id) {
        try {
            postService.deletePost(id);

            return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.POST_DELETE_SUCCESS), HttpStatus.OK);
        }  catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}/save")
    public ResponseEntity savePost(@PathVariable Long id) {
        try {
            User user = userService.getCurrentUser();
            postService.savePost(id, user);

            return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.POST_SAVE_SUCCESS), HttpStatus.OK);
        }  catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id]/share")
    public ResponseEntity sharePost(@PathVariable Long id, @RequestBody Long userId) {
        try {
            User user = userService.getCurrentUser();
            User shareUser = userService.findUserById(userId);

            postService.sharePost(user, shareUser, id);

            return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.POST_SHARE_SUCCESS), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.BAD_REQUEST);
        }
    }
}
