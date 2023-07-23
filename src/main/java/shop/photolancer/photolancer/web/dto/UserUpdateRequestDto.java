package shop.photolancer.photolancer.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import shop.photolancer.photolancer.domain.User;
import shop.photolancer.photolancer.domain.enums.Purpose;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "user의 회원정보 수정 dto")
public class UserUpdateRequestDto {
    private String nickname;
    private String explane;
    // 추후 북마크 임포트하는 작업 필요
    private String bookmark;


    public User toEntity(){
        User user = User.builder()
                .nickname(nickname)
                .explane(explane)
                .build();
        return user;
    }
}
