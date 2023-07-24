package shop.photolancer.photolancer.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import shop.photolancer.photolancer.domain.Post;
import shop.photolancer.photolancer.repository.PostBookmarkRepository;
import shop.photolancer.photolancer.service.PostBookmarkService;



@Service
@RequiredArgsConstructor
public class PostBookmarkServiceImpl implements PostBookmarkService {
    private final PostBookmarkRepository postBookmarkRepository;
    public Page<Post> postBookmarkList(Pageable request, String bookmarkName) {
        return postBookmarkRepository.findByPostBookmarkName(request, bookmarkName);
    }

}
