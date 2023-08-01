package shop.photolancer.photolancer.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.photolancer.photolancer.converter.PostConverter;
import shop.photolancer.photolancer.domain.Bookmark;
import shop.photolancer.photolancer.domain.Post;
import shop.photolancer.photolancer.domain.User;
import shop.photolancer.photolancer.domain.mapping.PostBookmark;
import shop.photolancer.photolancer.domain.mapping.PostImage;
import shop.photolancer.photolancer.domain.mapping.PostLike;
import shop.photolancer.photolancer.repository.*;
import shop.photolancer.photolancer.service.PostService;
import shop.photolancer.photolancer.web.dto.PostResponseDto;

import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final PostImgRepository postImgRepository;
    private final PostConverter postConverter;
    private final BookMarkServiceImpl bookmarkService;
    private final PostBookmarkRepository postBookmarkRepository;
    private final UserRepository userRepository;
    private final PostLikeRepository postLikeRepository;



    @Override
    public void upload(String content, Integer likeCount, Boolean isSale,
                       Integer point, List<String> imgPaths, List<String> bookmarkList) {

        Post post = postConverter.toPost(content, likeCount, point, isSale, imgPaths.get(0));
        postRepository.save(post);
        for (String imgUrl : imgPaths) {
            PostImage postImage = postConverter.toPostImage(imgUrl, post);
            postImgRepository.save(postImage);
        }
        for (String bookmarkName : bookmarkList) {
            Bookmark bookmark = bookmarkService.createBookmark(bookmarkName);
            PostBookmark postBookmark = postConverter.toPostBookmark(post, bookmark);
            postBookmarkRepository.save(postBookmark);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public PostResponseDto.PostDetailDto searchById(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(()
                -> new IllegalArgumentException("해당 게시물이 존재하지 않습니다."));
        List<PostImage> postImageList = postImgRepository.findByPostId(postId);
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

    @Override
    public void updateLike(Long postId, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(()
                -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));
        Post post = postRepository.findById(postId).orElseThrow(()
                -> new IllegalArgumentException("해당 게시물이 존재하지 않습니다."));
        try {
            PostLike postLike = postLikeRepository.findByPostAndUser(post, user);
            if(postLike == null) {
                postRepository.updateLikeCount(postId, post.getLikeCount()+1);
                postLikeRepository.save(postConverter.toPostLike(post, user));
            }
            else {
                postRepository.updateLikeCount(postId, post.getLikeCount()-1);
                postLikeRepository.delete(postLike);
            }
        }catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }
}
