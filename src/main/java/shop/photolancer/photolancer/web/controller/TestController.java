package shop.photolancer.photolancer.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.photolancer.photolancer.converter.TestConverter;
import shop.photolancer.photolancer.domain.Test;
import shop.photolancer.photolancer.exception.ResponseMessage;
import shop.photolancer.photolancer.exception.StatusCode;
import shop.photolancer.photolancer.service.TestService;
import shop.photolancer.photolancer.web.dto.TestRequestDto;
import shop.photolancer.photolancer.web.dto.TestResponseDto;
import shop.photolancer.photolancer.web.dto.base.DefaultRes;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
public class TestController {
    private final TestService testService;
    private final TestConverter testConverter;

    @PostMapping
    public ResponseEntity createTest(@RequestBody TestRequestDto.CreateTestDto request){
        System.out.println(request.getName());
        try {
            Test test = testService.create(request.getName());
            TestResponseDto.CreateTestDto res = testConverter.toCreateTestDto(test);
            return new ResponseEntity( DefaultRes.res(StatusCode.OK, ResponseMessage.TEST_SUCCESS, res), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }
}
