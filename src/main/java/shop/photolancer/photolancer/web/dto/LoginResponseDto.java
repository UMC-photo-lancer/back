package shop.photolancer.photolancer.web.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import shop.photolancer.photolancer.domain.User;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResponseDto {

    private User user;

    private String jwt;

    private String errorMessage;
}
