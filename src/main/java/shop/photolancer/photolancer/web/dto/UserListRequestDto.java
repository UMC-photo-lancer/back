package shop.photolancer.photolancer.web.dto;

import io.swagger.models.auth.In;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import shop.photolancer.photolancer.domain.User;
@Builder
@Getter
@Setter
@AllArgsConstructor
@Schema(description = "유저 전체 이름 반환")
public class UserListRequestDto {

}