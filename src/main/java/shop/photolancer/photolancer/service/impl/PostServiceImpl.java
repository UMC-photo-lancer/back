package shop.photolancer.photolancer.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.photolancer.photolancer.converter.PostConverter;
import shop.photolancer.photolancer.domain.Post;
import shop.photolancer.photolancer.domain.mapping.PostImage;
import shop.photolancer.photolancer.repository.ImgRepository;
import shop.photolancer.photolancer.repository.PostRepository;
import shop.photolancer.photolancer.service.PostService;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final ImgRepository imgRepository;
    private final PostConverter postConverter;
    @Override
    @Transactional
    public void upload(String content, Integer likeCount, Boolean isSale, Integer point, List<String> imgPaths) {
        Post post = postConverter.toPost(content, likeCount, point, isSale);
        postRepository.save(post).getId();
        List<String> imgList = new ArrayList<>();
        for (String imgUrl : imgPaths) {
            PostImage postImage = new PostImage(imgUrl, post);
            imgRepository.save(postImage);
            imgList.add(postImage.getUri());
        }
    }
}
