package shop.photolancer.photolancer.service;

import java.util.List;

public interface PostService {
    void upload(String content, Integer likeCount, Boolean isSale, Integer point, List<String> imgPaths);
}
