package shop.photolancer.photolancer.web.dto;

import lombok.Getter;
import lombok.Setter;

public class CommentRequestDto {
    @Getter
    @Setter
    public static class CommentUploadDto{
        private String content;
    }

    @Getter
    @Setter
    public static class RecommentUploadDto{
        private String content;
        private Long parentCommentId;
    }
}
