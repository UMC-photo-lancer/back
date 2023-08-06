package shop.photolancer.photolancer.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import shop.photolancer.photolancer.domain.User;
import shop.photolancer.photolancer.web.dto.UserResponseDto;

@RequiredArgsConstructor
@Component
public class UserConverter {
    public UserResponseDto.PostUserDto toUserProfile(User user) {
        return UserResponseDto.PostUserDto.builder()
                .id(user.getId())
                .level(user.getLevel())
                .profileUrl(user.getProfileUrl())
                .nickname(user.getNickname())
                .name(user.getName())
                .build();
    }
}
