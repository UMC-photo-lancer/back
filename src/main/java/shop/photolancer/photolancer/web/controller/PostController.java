package shop.photolancer.photolancer.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import shop.photolancer.photolancer.exception.ResponseMessage;
import shop.photolancer.photolancer.exception.StatusCode;
import shop.photolancer.photolancer.service.impl.PostImageUploadService;
import shop.photolancer.photolancer.service.impl.PostServiceImpl;
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
            postService.upload(content, likeCount, isSale, point, imgPaths, bookmarkList);

            return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.POST_UPLOAD_SUCCESS), HttpStatus.OK);
        } catch (Exception e){
        return new ResponseEntity(e, HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/{id}")
    public PostResponseDto.PostDetailDto postDetail(@PathVariable Long id) {
        return postService.searchById(id);
    }

    @PutMapping("/{id}/like")
    public ResponseEntity updateLike(@PathVariable Long id) {
        try {
             postService.updateLike(id, 5L);

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
}
