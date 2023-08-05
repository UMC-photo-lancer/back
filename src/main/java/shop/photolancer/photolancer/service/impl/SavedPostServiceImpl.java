package shop.photolancer.photolancer.service.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.photolancer.photolancer.domain.Post;
import shop.photolancer.photolancer.domain.User;
import shop.photolancer.photolancer.domain.mapping.SavedPost;
import shop.photolancer.photolancer.repository.SavedPostRepository;

@Service
@RequiredArgsConstructor
public class SavedPostServiceImpl {
    private final SavedPostRepository savedPostRepository;
    public Boolean isSavedPost(Post post, User user) {
        SavedPost savedPost = savedPostRepository.findByPostAndUser(post, user);

        if (savedPost == null) {
            return false;
        }
        else {
            return true;
        }
    }
}
