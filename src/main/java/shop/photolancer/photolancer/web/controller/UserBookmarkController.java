package shop.photolancer.photolancer.web.controller;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "사용자 북마크 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/bookmark")
@Slf4j
public class UserBookmarkController {

    @Operation(summary = "사용자의 북마크를 등록합니다.")
    @PostMapping(value = "/register")
    public ResponseEntity<?> registerBoookmark() {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
