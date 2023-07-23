package shop.photolancer.photolancer.web.controller;


import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import shop.photolancer.photolancer.domain.User;
import shop.photolancer.photolancer.domain.enums.UserStatus;
import shop.photolancer.photolancer.service.impl.UserServiceImpl;
import shop.photolancer.photolancer.web.dto.UserJoinRequestDto;

import java.util.Map;
import java.util.NoSuchElementException;

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

    // 휴면 계정, 즉 탈퇴한 계정일 경우의 회원가입 얘기하기
    @Operation(summary = "회원가입을 진행합니다.")
    @PostMapping(value = "/join")
    public ResponseEntity<?> createUser(@RequestBody UserJoinRequestDto userDto, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            Map<String, String> validatorResult = userServiceImpl.validateHandling(bindingResult);
            return new ResponseEntity<>(validatorResult, HttpStatus.BAD_REQUEST);
        }

        try {
            userServiceImpl.checkUsernameDuplication(userDto);
        } catch (IllegalStateException e){
            return new ResponseEntity<>("이름이 중복 됩니다.", HttpStatus.BAD_REQUEST);
        }

        try {
            userServiceImpl.checkIdDuplication(userDto);
        } catch (IllegalStateException e){
            return new ResponseEntity<>("Id가 중복 됩니다.", HttpStatus.BAD_REQUEST);
        }

        try {
            userServiceImpl.checkEmailDuplication(userDto);
        } catch (IllegalStateException e){
            return new ResponseEntity<>("이메일이 중복 됩니다.", HttpStatus.BAD_REQUEST);
        }

        try {
            userServiceImpl.checkPassword(userDto);
        } catch (IllegalStateException e){
            return new ResponseEntity<>("password값이 맞지 않습니다.", HttpStatus.CONFLICT);
        }


        userServiceImpl.createUser(userDto);
        return new ResponseEntity<>(1, HttpStatus.OK);
        // 프론트 측에서 auth/login으로 redirect필요
    }

    @Operation(summary = "회원 탈퇴를 진행합니다.")
    @PutMapping(value = "/withdrawal/{userId}")
    public ResponseEntity<?> withdrawUser(@PathVariable Long userId) {
        try {
            User user = userServiceImpl.findUserById(userId);
            userServiceImpl.deactivateUser(user);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("해당 사용자를 찾을 수 없습니다.", HttpStatus.NOT_FOUND);
        }
    }

}
