package shop.photolancer.photolancer.web.dto;

import io.swagger.models.auth.In;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Builder
@Setter
@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "user의 정보반환 dto")
public class UserInfoResponseDto {
    private Integer level;
    private Integer point;
    private String nickname;
    private String profileUrl;
    private String explane;
}
