package shop.photolancer.photolancer.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import shop.photolancer.photolancer.domain.User;
import shop.photolancer.photolancer.exception.ResponseMessage;
import shop.photolancer.photolancer.exception.StatusCode;
import shop.photolancer.photolancer.service.impl.PostImageService;
import shop.photolancer.photolancer.service.impl.PostServiceImpl;
import shop.photolancer.photolancer.service.impl.UserServiceImpl;
import shop.photolancer.photolancer.web.dto.PostRequestDto;
import shop.photolancer.photolancer.web.dto.PostResponseDto;
import shop.photolancer.photolancer.web.dto.base.DefaultRes;


import java.util.List;
import java.util.stream.Collectors;

@Api(tags = "POST 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {
    private final PostServiceImpl postService;
    private final PostImageService postImageService;
    private final UserServiceImpl userService;
    @ApiOperation(value = "POST 업로드 API")
    @PostMapping
    public ResponseEntity uploadPost(@RequestPart("postContent") PostRequestDto.PostUploadDto request,
                                     @RequestPart("imgFile") List<MultipartFile> multipartFiles) {
        try {
            List<String> imgPaths = postImageService.uploadAWS(multipartFiles);
            User user = userService.getCurrentUser();
            postService.upload(request, imgPaths, user);

            return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.POST_UPLOAD_SUCCESS), HttpStatus.OK);
        } catch (Exception e){
        return new ResponseEntity(e, HttpStatus.BAD_REQUEST);
        }
    }
    @ApiOperation(value = "POST 상세 조회 API")
    @GetMapping("/{id}")
    public PostResponseDto.PostDetailDto postDetail(@PathVariable Long id) {
        User user = userService.getCurrentUser();
        return postService.searchById(id, user);
    }

    @ApiOperation(value = "POST 좋아요 API")
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

    @ApiOperation(value = "POST 삭제 API")
    @DeleteMapping("/{id}")
    public ResponseEntity deletePost(@PathVariable Long id) {
        try {
            postService.deletePost(id);

            return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.POST_DELETE_SUCCESS), HttpStatus.OK);
        }  catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "POST 게시글 저장 API")
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

    @ApiOperation(value = "POST 공유 API")
    @PostMapping("/{id}/share")
    public ResponseEntity sharePost(@PathVariable Long id, @RequestBody PostRequestDto.PostShareDto userId) {
        try {
            User sharedBy = userService.getCurrentUser();
            List<User> shareTo = userId.getUserId().stream().map(
                    user -> userService.findUserById(user)
            ).collect(Collectors.toList());

            postService.sharePost(sharedBy, shareTo, id);

            return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.POST_SHARE_SUCCESS), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "POST 수정 API")
    @PutMapping("/{id}")
    public ResponseEntity updatePost(@PathVariable Long id, @RequestBody PostRequestDto.PostUpdateDto postUpdateDto) {
        try {
            postService.updatePost(id, postUpdateDto);

            return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.POST_UPDATE_SUCCESS), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.BAD_REQUEST);
        }
    }
}
