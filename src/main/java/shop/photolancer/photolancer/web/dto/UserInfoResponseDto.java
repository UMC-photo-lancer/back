package shop.photolancer.photolancer.web.dto;

import io.swagger.models.auth.In;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

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
    private String title;
    private Integer num_follower;
    private Integer num_post;
    private Integer num_following;
    private List<String> bookmark;
}
