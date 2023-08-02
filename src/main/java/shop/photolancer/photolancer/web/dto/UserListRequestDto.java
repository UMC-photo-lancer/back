package shop.photolancer.photolancer.web.dto;

import io.swagger.models.auth.In;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import shop.photolancer.photolancer.domain.User;

@Schema(description = "전체유저 반환")
public class UserListRequestDto {
    @Builder
    @Getter
    @Setter
    @AllArgsConstructor
    static public class Info {
        private Integer num_follower;
        private Integer num_post;
        private Integer num_following;
        private String explane;
        private List<String> bookmark;
    }

    public static UserListRequestDto.Info of (User user){
        return Info.builder()
//                .bookmark()
//                .num_follower()
//                .num_following()
//                .explane(user.getExplane())
//                .bookmark()
                .build();
    }
}