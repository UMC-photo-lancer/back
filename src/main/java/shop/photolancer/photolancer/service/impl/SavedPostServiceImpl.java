package shop.photolancer.photolancer.service.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public Long findSavedPost(Post post, User user) {
        SavedPost savedPost = savedPostRepository.findByPostAndUser(post, user);
        if (savedPost == null) {
            return null;
        }
        else {
            return savedPost.getId();
        }
    }

    public void savePost(SavedPost savedPost) {
        savedPostRepository.save(savedPost);
    }
    public void deleteSavedPost(Long savedPostId) {
        savedPostRepository.deleteById(savedPostId);
    }

    public Page<SavedPost> findUsersSavedPost(User user, Pageable pageable) {
        return savedPostRepository.findByUser(user, pageable);
    }
}
