package shop.photolancer.photolancer.service;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import shop.photolancer.photolancer.domain.Post;


public interface PostBookmarkService {
    Page<Post> postBookmarkList (Pageable request, String bookmarkName);
}
