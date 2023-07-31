package shop.photolancer.photolancer.web.controller;


import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import shop.photolancer.photolancer.domain.User;
import shop.photolancer.photolancer.domain.enums.UserStatus;
import shop.photolancer.photolancer.service.impl.UserServiceImpl;
import shop.photolancer.photolancer.web.dto.*;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.regex.Pattern;

@Api(tags = "사용자 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@Slf4j
public class UserController {
    // 1. 로그인 -> 탈퇴한 회원인지 체크하기
    // 2. 소셜로그인
    // 3. 회원가입(이름,이메일,비밀번호,비밀번호확인) -> done
    // 4. 비밀번호 찾기 -> done
    // 5. 회원정보 수정 (닉네임 -> Null일 경우 자기 닉네임,소개,북마크 설정) -> done/bookmark연동 필요
    // 6. 유저 Level,point, 닉네임, 한줄소개, 관심 키워드, 팔로워,포스트, 팔로잉, 타이틀 반환 -> 팔로워, 포스트수, 팔로잉 수, 타이틀 수정 필요
    // 7. 회원 탈퇴 -> done
    // 8. 유저 검색(nikname기반 검색 포스트 개수,팔로워수, 팔로잉 수, 한줄 소개 관심키워드 보여줌)
    // 9. 아이디 찾기 -> done
    // 10. 비밀번호 변경 -> done
    // 11. 유저 프로필 설정

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
    @PutMapping(value = "/withdrawal")
    public ResponseEntity<?> withdrawUser() {
        try {
            User user = userServiceImpl.getCurrentUser();
            userServiceImpl.deactivateUser(user);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("해당 사용자를 찾을 수 없습니다.", HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "회원 정보를 수정합니다.")
    @PutMapping(value = "/update")
    public ResponseEntity<?> updateUser(@RequestBody UserUpdateRequestDto requestDto) {
        try {
            userServiceImpl.checkUserNickNameDuplication(requestDto);
        } catch (IllegalStateException e){
            return new ResponseEntity<>("별명이 중복 됩니다.", HttpStatus.BAD_REQUEST);
        }
        try {
            User user = userServiceImpl.getCurrentUser();
            User updatedUser = userServiceImpl.updateUser(requestDto, user);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("해당 사용자를 찾을 수 없습니다.", HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "아이디를 찾습니다.")
    @GetMapping(value = "/find-id")
    public ResponseEntity<?> findUserId(@RequestParam String name, @RequestParam String email) {
        try {
            String userId = userServiceImpl.findUserIdByNameAndEmail(name, email);
            return new ResponseEntity<>(userId, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "유저의 full 아이디를 이메일을 통해 반환합니다.")
    @PostMapping(value = "find-id-mail")
    public ResponseEntity<?> findPassword(@RequestParam String name, @RequestParam String email){
        Map<String, Object> validResult = new HashMap<>();
        MailDTO mailDto = userServiceImpl.createMailAndFindId(name,email);
        userServiceImpl.mailSend(mailDto);
        validResult.put("success", "메일이 성공적으로 전송되었습니다.");

        return new ResponseEntity<>(validResult, HttpStatus.OK);
    }

    @Operation(summary = "비밀번호를 변경합니다.")
    @PutMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordDto changePasswordDto) {
        try {
            User user = userServiceImpl.getCurrentUser();
            String userName = user.getName();
            userServiceImpl.changePassword(userName, changePasswordDto);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "로그인을 진행합니다.")
    @PostMapping(value = "/login")
    public ResponseEntity<String> login(@RequestBody UserLoginDto userLoginDto) {
        try {
            String user_id = userLoginDto.getUser_id();
            String password = userLoginDto.getPassword();
            String result = userServiceImpl.login(user_id, password);
            return ResponseEntity.ok().body(result);
        }catch (AuthenticationCredentialsNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @Operation(summary = "비밀번호를 찾습니다.")
    @PostMapping(value = "find-pw")
    public ResponseEntity<?> findPassword(@RequestParam("memberEmail") String memberEmail){
        Map<String, Object> validResult = new HashMap<>();

        if(!Pattern.matches("^[a-zA-Z0-9+-\\_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$", memberEmail)) {
            validResult.put("valid_email", "올바르지 않은 이메일 형식입니다.");
            return new ResponseEntity<>(validResult, HttpStatus.BAD_REQUEST);
        }
        MailDTO mailDto = userServiceImpl.createMailAndChangePassword(memberEmail);
        userServiceImpl.mailSend(mailDto);
        validResult.put("success", "메일이 성공적으로 전송되었습니다.");

        return new ResponseEntity<>(validResult, HttpStatus.OK);
    }

    @Operation(summary = "유저의 정보를 반환합니다.")
    @GetMapping(value = "info")
    public ResponseEntity<?> getUserInformation(){
        //Level,point, 닉네임, 한줄소개, 관심 키워드, 팔로워,포스트, 팔로잉, 타이틀 반환
        // level, point, nickname, profile_url, explane
        // 팔로워 전체수 반환, 필로잉 전체 수 반환, 포스트 전체 수 반환
        // 현재 로그인 된 사용자의 이름 (아이디) 을 가지고 옵니다.
        User user = userServiceImpl.getCurrentUser();

        // UserInfoResponse DTO를 생성합니다.
        UserInfoResponseDto userInfoResponse = new UserInfoResponseDto();
        userInfoResponse.setLevel(user.getLevel());
        userInfoResponse.setPoint(user.getPoint());
        userInfoResponse.setNickname(user.getNickname());
        userInfoResponse.setProfileUrl(user.getProfileUrl());
        userInfoResponse.setExplane(user.getExplane());

        // DTO를 리턴합니다.
        return ResponseEntity.ok(userInfoResponse);
    }
}
