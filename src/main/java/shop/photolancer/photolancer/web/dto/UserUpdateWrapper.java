package shop.photolancer.photolancer.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserUpdateWrapper {
    private UserUpdateRequestDto userUpdateRequest;
    private BookmarkDto bookmarkDto;
}
