package shop.photolancer.photolancer.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.photolancer.photolancer.converter.PostConverter;
import shop.photolancer.photolancer.domain.Bookmark;
import shop.photolancer.photolancer.domain.Post;
import shop.photolancer.photolancer.domain.mapping.PostBookmark;
import shop.photolancer.photolancer.domain.mapping.PostImage;
import shop.photolancer.photolancer.repository.ImgRepository;
import shop.photolancer.photolancer.repository.PostBookmarkRepository;
import shop.photolancer.photolancer.repository.PostRepository;
import shop.photolancer.photolancer.service.PostService;
import shop.photolancer.photolancer.web.dto.PostResponseDto;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final ImgRepository imgRepository;
    private final PostConverter postConverter;
    private final BookMarkServiceImpl bookmarkService;
    private final PostBookmarkRepository postBookmarkRepository;


    @Override
    @Transactional
    public void upload(String content, Integer likeCount, Boolean isSale,
                       Integer point, List<String> imgPaths, List<String> bookmarkList) {

        Post post = postConverter.toPost(content, likeCount, point, isSale);
        postRepository.save(post).getId();
        List<String> imgList = new ArrayList<>();
        for (String imgUrl : imgPaths) {
            PostImage postImage = new PostImage(imgUrl, post);
            imgRepository.save(postImage);
            imgList.add(postImage.getUri());
        }
        for (String bookmarkName : bookmarkList) {
            Bookmark bookmark = bookmarkService.createBookmark(bookmarkName);
            PostBookmark postBookmark = new PostBookmark(post, bookmark);
            postBookmarkRepository.save(postBookmark);
        }
    }

    @Override
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public PostResponseDto.PostDetailDto searchById(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(()
                -> new IllegalArgumentException("해당 게시물이 존재하지 않습니다."));
        List<PostImage> postImageList = imgRepository.findByPostId(postId);
        List<String> postImageUri = new ArrayList<>();
        List<PostBookmark> postBookmarkList = postBookmarkRepository.findByPostIdWithBookmark(postId);
        List<String> postBookmarkNameList = new ArrayList<>();

        for (PostImage p : postImageList) {
            postImageUri.add(p.getUri());
        }
        for (PostBookmark p : postBookmarkList) {
            postBookmarkNameList.add(p.getBookmark().getName());
        }
        return  postConverter.toPostDetail(post, postImageUri, postBookmarkNameList);
    }
}
