package shop.photolancer.photolancer.web.dto;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookmarkDto {
    private List<String> content;

    @Override
    public String toString() {
        return "RegisterBookmarkDto{" +
                "content=" + content +
                '}';
    }
}
