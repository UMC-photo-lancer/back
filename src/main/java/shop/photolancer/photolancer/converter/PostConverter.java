package shop.photolancer.photolancer.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import shop.photolancer.photolancer.domain.Post;

@RequiredArgsConstructor
@Component
public class PostConverter {
    public Post toPost(String content, Integer likeCount, Integer point, Boolean isSale) {
        return Post.builder()
//                .user(user)
                .content(content)
                .likeCount(likeCount)
                .isSale(isSale)
                .point(point)
                .build();
    }
}
