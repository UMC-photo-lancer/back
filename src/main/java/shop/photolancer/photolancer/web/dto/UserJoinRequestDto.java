package shop.photolancer.photolancer.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import shop.photolancer.photolancer.domain.User;
import shop.photolancer.photolancer.domain.enums.Purpose;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "user의 회원가입dto")
public class UserJoinRequestDto {

    @NotBlank(message = "사용자 이름은 필수 입력 값입니다.")
    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9-_]{2,10}$", message = "이름은 특수문자를 제외한 2~10자리여야 합니다.")
    private String name;

    @NotBlank(message = "아이디는 필수 입력 값입니다.")
    @Pattern(regexp = "^[a-zA-Z0-9]{8,15}$", message = "아이디는 8자 이상 15자 이하의 영문과 숫자의 조합이어야 합니다.")
    private String user_id;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
    private String password;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
    private String again_password;

    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$", message = "유효한 이메일 주소를 입력하세요.")
    private String email;

//    @NotBlank(message = "목적은 필수 입력 값입니다.")
//    @Enumerated(EnumType.STRING)
//    private String purpose;

    /* DTO -> Entity */
    public User toEntity(){
        User user = User.builder()
                .name(name)
                .password(password)
                .userId(user_id)
//                .purpose(Purpose.fromStringIgnoreCase(this.purpose))
                .email(email)
                .build();
        return user;
    }

}
