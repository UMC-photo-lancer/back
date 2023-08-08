package shop.photolancer.photolancer.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import shop.photolancer.photolancer.config.Login.utils.JwtUtil;
import shop.photolancer.photolancer.domain.User;
import shop.photolancer.photolancer.domain.enums.Purpose;
import shop.photolancer.photolancer.domain.enums.Role;
import shop.photolancer.photolancer.domain.enums.UserStatus;
import shop.photolancer.photolancer.repository.UserRepository;
import shop.photolancer.photolancer.web.dto.*;

import javax.transaction.Transactional;
import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl {
    protected final UserRepository userRepository;
    protected final PasswordEncoder passwordEncoder;
    protected final JavaMailSender javaMailSender;

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

    // 만약 데이터가 존재한다면 IllegalStateException이 일어나게 됨 -> 500error
    public void checkUsernameDuplication(UserJoinRequestDto userDto) {
        boolean usernameDuplicate = userRepository.existsByName(userDto.toEntity().getName());
        if (usernameDuplicate) {
            throw new IllegalStateException("이미 존재하는 닉네임입니다.");
        }
    }
    public void checkIdDuplication(UserJoinRequestDto userDto) {
        boolean IdDuplicate = userRepository.existsByUserId(userDto.toEntity().getUserId());
        if (IdDuplicate) {
            throw new IllegalStateException("이미 존재하는 아이디입니다.");
        }
    }

    public void checkEmailDuplication(UserJoinRequestDto userDto) {
        boolean emailDuplicate = userRepository.existsByEmail(userDto.toEntity().getEmail());
        if (emailDuplicate) {
            throw new IllegalStateException("이미 존재하는 이메일입니다.");
        }
    }

    public void checkPassword(UserJoinRequestDto userDto){
        boolean passwordCheck = userDto.getPassword().equals(userDto.getAgain_password()) ? true : false ;
        if (!passwordCheck) {
            throw new IllegalStateException("비밀번호가 일치하지 않습니다.");
        }
    }

    public void checkUserNickNameDuplication(UserUpdateRequestDto requestDto) {
        boolean userNickNameDuplicate = userRepository.existsByNickname(requestDto.toEntity().getNickname());
        if (userNickNameDuplicate) {
            throw new IllegalStateException("이미 존재하는 닉네임입니다.");
        }
    }

    public User findUserById(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        } else {
            throw new NoSuchElementException("No user found with id " + userId);
        }
    }

    public User findUserByUserName(String userName) {
        Optional<User> optionalUser = userRepository.findByName(userName);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        } else {
            throw new NoSuchElementException("No user found with name " + userName);
        }
    }

    public void createUser(UserJoinRequestDto userInfo) {
        User user = User.builder()
                .userId(userInfo.getUser_id())
                .password(userInfo.getPassword())
                .name(userInfo.getName())
                .email(userInfo.getEmail())
                .purpose(Purpose.valueOf(userInfo.getPurpose()))
                .status(UserStatus.ACTIVE)
                .role(Role.GUEST)
                .point(0)
                .level(1)
                .num_follower(0)
                .num_following(0)
                .num_post(0)
                .num_notification(0)
                .experience(0.0)
                .explane("")
                .isPro(false)
                .build();

        // 사용자 등록
        user.passwordEncode(passwordEncoder);
        userRepository.save(user);
    }

    public User deactivateUser(User user) {
        user.setStatus(UserStatus.INACTIVE);
        return userRepository.save(user);
    }

    public User updateUser(UserUpdateRequestDto requestDto,User user) {
        user.setNickname(requestDto.getNickname());
        user.setExplane(requestDto.getExplane());
//        user.setBookmark(requestDto.getBookmark());
        return userRepository.save(user);
    }

    public String findUserIdByNameAndEmail(String name, String email) {
        User user = userRepository.findByNameAndEmail(name, email)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다."));
        if(user.getStatus().equals(UserStatus.INACTIVE)) {
            throw new IllegalArgumentException("해당 사용자는 탈퇴한 사용자입니다.");
        }
        String userId = user.getUserId();
        // 사용자 아이디 뒷 4자리를 *로 바꿔서 보내야함
        if(userId.length() <= 4) {
            // 사용자 ID가 4자리 이하인 경우 전체를 *로 변경
            char[] asterisks = new char[userId.length()];
            Arrays.fill(asterisks, '*');
            return new String(asterisks);
        } else {
            // 사용자 아이디 뒷 4자리를 *로 바꿈
            String front = userId.substring(0, userId.length() - 4);
            return front + "****";
        }
    }

    //임시 비밀번호로 업데이트
    public void updatePassword(String tmpPw, String userEmail){
        String memberPassword = tmpPw;
        Long memberId = userRepository.findByEmail(userEmail).getId();
        userRepository.updatePassword(memberId,passwordEncoder.encode(memberPassword));
    }

    //랜덤함수로 임시비밀번호 구문 만들기
    public String getTempPassword(){
        char[] charSet = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
                'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
        String tmpPw = "";
        // 문자 배열 길이의 값을 랜덤으로 10개를 뽑아 구문을 작성함
        int idx = 0;
        for (int i = 0; i < 10; i++) {
            idx = (int) (charSet.length * Math.random());
            tmpPw += charSet[idx];
        }
        return tmpPw;
    }

    public String login(String userId, String password) {
        // 사용자 id를 사용하여 사용자를 데이터베이스에서 조회
        User user = userRepository.findByUserId(userId);

        if (user == null) {
            throw new AuthenticationCredentialsNotFoundException("아이디가 잘못되었습니다.");
        }

        // 조회한 사용자 정보와 입력한 비밀번호를 비교하여 일치하는지 확인합니다.
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new AuthenticationCredentialsNotFoundException("비밀번호가 잘못되었습니다.");
        }
        // 탈퇴한 회원인지 확인
        if (user.getStatus().equals(UserStatus.INACTIVE)){
            throw new AuthenticationCredentialsNotFoundException("탈퇴한 회원입니다.");
        }
        String userName = user.getName();
        return JwtUtil.createJwt(userName, secretKey,expireMs);
    }
    public void changePassword(String userName, ChangePasswordDto changePasswordDto) {
            User user = userRepository.findByName(userName)
                    .orElseThrow(() -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다."));
            if (passwordEncoder.matches(changePasswordDto.getNewPassword(), user.getPassword())){
                throw new IllegalArgumentException("입력한 비밀번호가 이전 비밀번호와 같습니다.");
            }
            if (!changePasswordDto.getNewPassword().equals(changePasswordDto.getNewAgainPassword())) {
                throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
            }
            user.setPassword(passwordEncoder.encode(changePasswordDto.getNewPassword()));
            userRepository.save(user);
    }
    public User getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "로그인 되지 않았습니다."
            );
        }
        String userName = authentication.getName();
        User user = findUserByUserName(userName);
        if(user.getStatus().equals(UserStatus.INACTIVE)) {
            throw new IllegalArgumentException("해당 사용자는 탈퇴한 사용자입니다.");
        }
        return user;
    }

    public MailDTO createMailAndChangePassword(String memberEmail) {
        String tmpPw = getTempPassword();
        MailDTO mailDto = new MailDTO();
        mailDto.setAddress(memberEmail);
        mailDto.setTitle("Photo Lancer 임시비밀번호 안내 이메일 입니다.");
        mailDto.setMessage("안녕하세요. Photo Lancer 임시비밀번호 안내 관련 이메일 입니다." + " 회원님의 임시 비밀번호는 "
                + tmpPw + " 입니다." + "로그인 후에 비밀번호를 변경을 해주세요");
        updatePassword(tmpPw,memberEmail);
        return mailDto;
    }

    public MailDTO createMailAndFindId(String name, String email) {
        User user = userRepository.findByNameAndEmail(name, email)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다."));
        if(user.getStatus().equals(UserStatus.INACTIVE)) {
            throw new IllegalArgumentException("해당 사용자는 탈퇴한 사용자입니다.");
        }
        String userId = user.getUserId();
        MailDTO mailDto = new MailDTO();
        mailDto.setAddress(email);
        mailDto.setTitle("Photo Lancer 아이디 안내 이메일 입니다.");
        mailDto.setMessage("안녕하세요. Photo Lancer 아이디 안내 관련 이메일 입니다." + " 회원님의 아이디는 "
                + userId + " 입니다.");
        return mailDto;
    }

    public void mailSend(MailDTO mailDto) {
        log.info("메일 전송 완료!");
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailDto.getAddress());
        message.setSubject(mailDto.getTitle());
        message.setText(mailDto.getMessage());
        message.setFrom("rmatlr0112@gmail.com");
        message.setReplyTo("rmatlr0112@gmail.com");
        System.out.println("message"+message);
        javaMailSender.send(message);
    }

//    public void uploadProfile(String bucketName, String userName, MultipartFile file) {
//        try {
//            String originalFileName = file.getOriginalFilename();
//            String savedFileName = UUID.randomUUID().toString() + "_" + originalFileName;
//
////            String fileUrl = s3Service.uploadFile(savedFileName); // S3에 파일 업로드 후 URL 반환
//
////            User save_profile = UserRepository.save(
////                    User.builder()
////                            .profile_url(fileUrl)
////                            .build();
////            )
////            File fileUploadEntity = new File(originalFileName, savedFileName, fileUrl);
////            return fileUrl;
//        } catch (IOException e) {
//            return "Error occurred while uploading file.";
//        }
//    }

//    public List<UserListRequestDto.Info> getaAllUser() {
//        return userRepository.map(UserInfoResponseDto.Info::of).collect(Collectors.toList())
//    }
}
