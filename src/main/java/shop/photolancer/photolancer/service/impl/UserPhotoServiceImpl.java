package shop.photolancer.photolancer.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import shop.photolancer.photolancer.domain.Post;
import shop.photolancer.photolancer.domain.User;
import shop.photolancer.photolancer.domain.mapping.UserPhoto;
import shop.photolancer.photolancer.repository.UserPhotoRepository;

@Service
@RequiredArgsConstructor
public class UserPhotoServiceImpl {
    private final UserPhotoRepository userPhotoRepository;
    public Boolean isUserPhoto (Post post, User user) {
        UserPhoto isUserPhoto = userPhotoRepository.findByUserAndPost(user, post);
        if (isUserPhoto == null) {
            return false;
        }
        else {
            return true;
        }
    }

    public Page<UserPhoto> findUserPhotoByUser(User user, Pageable pageable) {
        return userPhotoRepository.findByUser(user, pageable);
    }
}
