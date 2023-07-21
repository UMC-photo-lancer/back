package shop.photolancer.photolancer.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import shop.photolancer.photolancer.domain.User;
import shop.photolancer.photolancer.domain.enums.Purpose;
import shop.photolancer.photolancer.repository.UserRepository;
import shop.photolancer.photolancer.web.dto.UserJoinRequestDto;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j

public class UserServiceImpl {
    protected final UserRepository userRepository;
//    protected final PasswordEncoder passwordEncoder;

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expireMs}")
    private Long expireMs;
    public Map<String, String> validateHandling(BindingResult bindingResult) {
        Map<String, String> validatorResult = new HashMap<>();
        // 유효성 검사에 실패한 필드들을 Map 자료구조를 통해 {키 값, 에러 메시지}형태로 응답
        // Key : valid_{dto 필드명}, Message : dto에서 작성한 Message값
        for(FieldError error : bindingResult.getFieldErrors()) {
            String validKeyName = String.format("valid_%s", error.getField());
            validatorResult.put(validKeyName, error.getDefaultMessage());
        }
        return validatorResult;
    }

    public void createUser(UserJoinRequestDto userInfo) {

        User user = User.builder()
                .user_id(userInfo.getUser_id())
                .password(userInfo.getPassword())
                .name(userInfo.getName())
                .purpose(Purpose.valueOf(userInfo.getPurpose()))
                .build();

        // 사용자 등록
//        user.passwordEncode(passwordEncoder);
        userRepository.save(user);
    }
    //소셜 로그인에 쓰일 함수
    public void createUser(User user){
        userRepository.save(user);
    }

    // 만약 데이터가 존재한다면 IllegalStateException이 일어나게 됨 -> 500error
    public void checkUsernameDuplication(UserJoinRequestDto userDto) {
        boolean usernameDuplicate = userRepository.existsByName(userDto.toEntity().getName());
        if (usernameDuplicate) {
            throw new IllegalStateException("이미 존재하는 닉네임입니다.");
        }
    }
    public void checkIdDuplication(UserJoinRequestDto userDto) {
        boolean emailDuplicate = userRepository.existsByUser_id(userDto.toEntity().getUser_id());
        if (emailDuplicate) {
            throw new IllegalStateException("이미 존재하는 아이디입니다.");
        }
    }

}
