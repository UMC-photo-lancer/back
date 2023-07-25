package shop.photolancer.photolancer.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.photolancer.photolancer.exception.ResponseMessage;
import shop.photolancer.photolancer.exception.StatusCode;
import shop.photolancer.photolancer.service.impl.CommentServiceImpl;
import shop.photolancer.photolancer.web.dto.CommentRequestDto;
import shop.photolancer.photolancer.web.dto.base.DefaultRes;


@RestController
@RequiredArgsConstructor
@RequestMapping("/post/{postId}/comment")
public class CommentController {
    public final CommentServiceImpl commentService;
    @PostMapping
    public ResponseEntity uploadComment(@RequestBody CommentRequestDto.CommentUploadDto request,
                                        @PathVariable Long postId) {
        try {
            commentService.uploadComment(request, 1L, postId);

            return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.COMMENT_UPLOAD_SUCCESS), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity(e, HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/{commentId}")
    public ResponseEntity uploadRecomment(@RequestBody CommentRequestDto.CommentUploadDto request,
                                          @PathVariable Long postId, @PathVariable Long commentId) {
        try {
            commentService.uploadRecomment(request, 1L, postId, commentId);

            return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.RECOMMENT_UPLOAD_SUCCESS), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity(e, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity deleteComment(@PathVariable Long commentId) {
        try {
            commentService.deleteComment(commentId);
            return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.COMMENT_DELETE_SUCCESS), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.BAD_REQUEST);
        }
    }
}
