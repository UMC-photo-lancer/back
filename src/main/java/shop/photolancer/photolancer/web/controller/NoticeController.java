package shop.photolancer.photolancer.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import shop.photolancer.photolancer.domain.enums.Category;
import shop.photolancer.photolancer.exception.ResponseMessage;
import shop.photolancer.photolancer.exception.StatusCode;
import shop.photolancer.photolancer.service.NoticeService;
import shop.photolancer.photolancer.service.impl.NoticeFileUploadService;
import shop.photolancer.photolancer.web.dto.NoticeRequestDto;
import shop.photolancer.photolancer.web.dto.base.DefaultRes;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notice")
public class NoticeController {
    private final NoticeService noticeService;
    private final NoticeFileUploadService noticeFileUploadService;
    @PostMapping
    public ResponseEntity noticeUpload(@RequestPart("NoticeContent") NoticeRequestDto.NoticeUploadDto request,
                             @RequestPart("NoticeFile") List<MultipartFile> multipartFiles) {
        try {
            List<String> filePaths = noticeFileUploadService.upload(multipartFiles);
            String content = request.getContent();
            String title = request.getTitle();
            Category category = request.getCategory();
            Boolean isPublic = request.getIsPublic();
            noticeService.upload(content, title, category, isPublic, filePaths);
            return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.NOTICE_UPLOAD_SUCCESS), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.BAD_REQUEST);
        }
    }
}