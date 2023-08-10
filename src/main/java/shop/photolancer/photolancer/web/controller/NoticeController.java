package shop.photolancer.photolancer.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import shop.photolancer.photolancer.converter.NoticeConverter;
import shop.photolancer.photolancer.domain.Notice;
import shop.photolancer.photolancer.domain.User;
import shop.photolancer.photolancer.domain.enums.Category;
import shop.photolancer.photolancer.domain.enums.Role;
import shop.photolancer.photolancer.exception.ResponseMessage;
import shop.photolancer.photolancer.exception.StatusCode;
import shop.photolancer.photolancer.service.NoticeService;
import shop.photolancer.photolancer.service.impl.NoticeFileServiceImpl;
import shop.photolancer.photolancer.service.impl.UserServiceImpl;
import shop.photolancer.photolancer.web.dto.NoticeRequestDto;
import shop.photolancer.photolancer.web.dto.NoticeResponseDto;
import shop.photolancer.photolancer.web.dto.base.DefaultRes;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notice")
public class NoticeController {
    private final NoticeService noticeService;
    private final NoticeFileServiceImpl noticeFileServiceImpl;
    private final UserServiceImpl userService;
    private final NoticeConverter noticeConverter;
    @PostMapping
    public ResponseEntity noticeUpload(
            @RequestPart(value = "NoticeContent") NoticeRequestDto.NoticeUploadDto request,
            @RequestPart(value = "NoticeFile", required = false) List<MultipartFile> multipartFiles) {
        try {
            User user = userService.getCurrentUser();
            if (user.getRole() == Role.ADMIN) {
                String content = request.getContent();
                String title = request.getTitle();
                Category category = request.getCategory();
                Boolean isPublic = request.getIsPublic();
                System.out.println(multipartFiles);
                List<String> filePaths = null;
                if (multipartFiles != null && !multipartFiles.isEmpty()) {
                    filePaths = noticeFileServiceImpl.uploadAWS(multipartFiles);
                    noticeService.upload(content, title, category, isPublic, filePaths, user);
                }
                else {
                    noticeService.upload(content, title, category, isPublic, user);
                }
                return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.NOTICE_UPLOAD_SUCCESS), HttpStatus.OK);
            }
            return new ResponseEntity(DefaultRes.res(StatusCode.BAD_REQUEST, ResponseMessage.NOTICE_NOT_ADMIN), HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            return new ResponseEntity(DefaultRes.res(StatusCode.BAD_REQUEST, ResponseMessage.INVALID_REQUEST), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public NoticeResponseDto.NoticeListDto noticePage(@PageableDefault(size = 12, sort = "createdAt",
            direction = Sort.Direction.DESC) Pageable request) {
        try {
            User user = userService.getCurrentUser();
            if (user.getRole() == Role.ADMIN) {
                return noticeConverter.toNoticeList(noticeService.noticePage(request), true);
            }
            return noticeConverter.toNoticeList(noticeService.noticePage(request), false);
        } catch (Exception e) {
            return null;
        }
    }

    @GetMapping("/category/{category}")
    public NoticeResponseDto.NoticeListDto noticePageCategory(
            @PathVariable("category") String categoryValue,
            @PageableDefault(size = 12, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageRequest) {
        User user = userService.getCurrentUser();
        Category category = Category.valueOf(categoryValue.toUpperCase());
        if (user.getRole() == Role.ADMIN) {
            return noticeConverter.toNoticeList(noticeService.noticePageCategory(category, pageRequest), true);
        }
        return noticeConverter.toNoticeList(noticeService.noticePageCategory(category, pageRequest), false);
    }

    // 공지게시판 상세 조회
    @GetMapping("/{id}")
    public NoticeResponseDto.NoticeDetailDto noticeDetail(@PathVariable Long id) {
        User user = userService.getCurrentUser();
        Notice notice = noticeService.findNoticeById(id);
        List<NoticeResponseDto.NoticeFileDto> noticeFile = noticeService.findNoticeFileByNotice(notice);
        if (user.getRole() == Role.ADMIN) {
            return noticeConverter.toNoticeDetail(notice, noticeFile, true);
        }
        return noticeConverter.toNoticeDetail(notice, noticeFile, false);
    }

    // 공지 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity deleteNotice(@PathVariable Long id) {
        User user = userService.getCurrentUser();
        try {
            if (user.getRole() == Role.ADMIN) {
                noticeService.deleteNotice(id);
                return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.NOTICE_DELETE_SUCCESS), HttpStatus.OK);
            }
            else {
                return new ResponseEntity(DefaultRes.res(StatusCode.BAD_REQUEST, ResponseMessage.NOTICE_NOT_ADMIN), HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity(DefaultRes.res(StatusCode.BAD_REQUEST, ResponseMessage.INVALID_REQUEST), HttpStatus.BAD_REQUEST);
        }
    }

    // 공지 수정
    @PutMapping("{id}")
    public ResponseEntity updateNotice(@PathVariable Long id,
                                       @RequestPart(value = "NoticeContent") NoticeRequestDto.NoticeUpdateDto request
            ,@RequestPart(value = "NoticeFile", required = false) List<MultipartFile> multipartFiles) {
        User user = userService.getCurrentUser();
        try {
            if (user.getRole() == Role.ADMIN) {
                List<String> filePaths = null;
                if (multipartFiles != null && !multipartFiles.isEmpty()) {
                    filePaths = noticeFileServiceImpl.uploadAWS(multipartFiles);
                    noticeService.updateNotice(id, request, filePaths);
                }
                else {
                    noticeService.updateNotice(id, request);
                }
                return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.NOTICE_UPDATE_SUCCESS), HttpStatus.OK);
            }
            else {
                return new ResponseEntity(DefaultRes.res(StatusCode.BAD_REQUEST, ResponseMessage.NOTICE_NOT_ADMIN), HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity(DefaultRes.res(StatusCode.BAD_REQUEST, ResponseMessage.INVALID_REQUEST), HttpStatus.BAD_REQUEST);
        }
    }
}