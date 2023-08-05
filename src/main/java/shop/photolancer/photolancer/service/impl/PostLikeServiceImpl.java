package shop.photolancer.photolancer.service.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.photolancer.photolancer.domain.Post;
import shop.photolancer.photolancer.domain.User;
import shop.photolancer.photolancer.domain.mapping.PostLike;
import shop.photolancer.photolancer.repository.PostLikeRepository;

@Service
@RequiredArgsConstructor
public class PostLikeServiceImpl {
    private final PostLikeRepository postLikeRepository;
    public Boolean likeStatus(Post post, User user) {
        PostLike postLike = postLikeRepository.findByPostAndUser(post, user);
        if (postLike == null) {
            return false;
        }
        else {
            return true;
        }
    }
}
