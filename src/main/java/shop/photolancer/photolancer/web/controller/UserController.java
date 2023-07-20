package shop.photolancer.photolancer.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.photolancer.photolancer.service.impl.UserServiceImpl;
import shop.photolancer.photolancer.web.dto.UserJoinRequestDto;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    // 1. 로그인
    // 2. 소셜로그인
    // 3. 회원가입(이름,이메일,비밀번호,비밀번호확인)
    // 4. 비밀번호 찾기
    // 5. 회원정보 수정
    // 6. 이미 가입된 아이디인지, 메일인지 확인하기 -> response값을 뭐라 해야할까
    // 7. 회원 탈퇴
    private final UserServiceImpl userServiceImpl;


    @Operation(summary = "회원가입을 진행합니다.")
    @PostMapping(value = "/join")
    public ResponseEntity<?> createUser(@RequestBody UserJoinRequestDto userDto, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            Map<String, String> validatorResult = userServiceImpl.validateHandling(bindingResult);
            return new ResponseEntity<>(validatorResult, HttpStatus.BAD_REQUEST);
        }

        userServiceImpl.checkUsernameDuplication(userDto);
        userServiceImpl.checkIdDuplication(userDto);

        userServiceImpl.createUser(userDto);
        return new ResponseEntity<>(1, HttpStatus.OK);
        // 프론트 측에서 auth/login으로 redirect필요
    }
}
