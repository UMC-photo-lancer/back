package shop.photolancer.photolancer.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import shop.photolancer.photolancer.converter.PostConverter;
import shop.photolancer.photolancer.domain.Bookmark;
import shop.photolancer.photolancer.domain.Notification;
import shop.photolancer.photolancer.domain.Post;
import shop.photolancer.photolancer.domain.User;
import shop.photolancer.photolancer.domain.mapping.*;
import shop.photolancer.photolancer.repository.*;
import shop.photolancer.photolancer.service.PostService;
import shop.photolancer.photolancer.web.dto.PostRequestDto;
import shop.photolancer.photolancer.web.dto.PostResponseDto;

import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final PostImgRepository postImgRepository;
    private final PostConverter postConverter;
    private final BookmarkServiceImpl bookmarkService;
    private final PostBookmarkRepository postBookmarkRepository;
    private final PostLikeServiceImpl postLikeService;
    private final SavedPostServiceImpl savedPostService;
    private final UserPhotoServiceImpl userPhotoService;
    private final UserServiceImpl userService;
    private final NotificationRepository notificationRepository;
    private final PostBookmarkServiceImpl postBookmarkService;



    @Override
    public void upload(String content, Integer likeCount, Boolean isSale,
                       Integer point, List<String> imgPaths, List<String> bookmarkList,
                       User user) {

        Post post = postConverter.toPost(content, likeCount, point, isSale, imgPaths.get(0), user);
        postRepository.save(post);
        user.setNum_post(user.getNum_post() + 1);
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
    public PostResponseDto.PostDetailDto searchById(Long postId, User user) {
        Post post = postRepository.findById(postId).orElseThrow(()
                -> new IllegalArgumentException("해당 게시물이 존재하지 않습니다."));
        List<PostImage> postImageList = postImgRepository.findByPostId(postId);
        List<String> postImageUri = new ArrayList<>();
        List<PostBookmark> postBookmarkList = postBookmarkRepository.findByPostIdWithBookmark(postId);
        List<String> postBookmarkNameList = new ArrayList<>();
        Boolean savedPost = savedPostService.isSavedPost(post, user);
        Boolean postLike = postLikeService.likeStatus(post, user);

        for (PostImage p : postImageList) {
            postImageUri.add(p.getUri());
        }
        for (PostBookmark p : postBookmarkList) {
            postBookmarkNameList.add(p.getBookmark().getName());
        }

        Boolean isUserPhoto = userPhotoService.isUserPhoto(post, user);

        return  postConverter.toPostDetail(post, postImageUri, postBookmarkNameList, isUserPhoto, postLike, savedPost);
    }

    @Override
    public void updateLike(Long postId, User user) {
        Post post = postRepository.findById(postId).orElseThrow(()
                -> new IllegalArgumentException("해당 게시물이 존재하지 않습니다."));
        try {
            Long postLikeId = postLikeService.findPostLike(post, user);
            if(postLikeId == null) {
                postRepository.updateLikeCount(postId, post.getLikeCount()+1);
                postLikeService.updateLike(postConverter.toPostLike(post, user));
            }
            else {
                postRepository.updateLikeCount(postId, post.getLikeCount()-1);
                postLikeService.deleteLike(postLikeId);
            }
        }catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }

    @Override
    public void savePost(Long postId, User user) {
        Post post = postRepository.findById(postId).orElseThrow();
        Long savedPostId = savedPostService.findSavedPost(post, user);

        if (savedPostId == null) {
            savedPostService.savePost(postConverter.toSavedPost(user, post));
        }
        else {
            savedPostService.deleteSavedPost(savedPostId);
        }
    }

    @Override
    public Page<PostResponseDto.PostListDto> savedPosts(Pageable pageable) {
        User user = userService.getCurrentUser();
        Page<SavedPost> myPostList = savedPostService.findUsersSavedPost(user, pageable);
        Page<PostResponseDto.PostListDto> myPostPage = myPostList.map(
                myPost -> {
                    Boolean savedPost = savedPostService.isSavedPost(myPost.getPost(), user);
                    Boolean likeStatus = postLikeService.likeStatus(myPost.getPost(), user);
                    Boolean isUserPhoto = userPhotoService.isUserPhoto(myPost.getPost(), user);
                    PostResponseDto.PostListDto postListDto = postConverter.toPostList(myPost.getPost(), isUserPhoto,  savedPost, likeStatus);

                    return postListDto;
                });
        return myPostPage;
    }

    @Override
    public Page<PostResponseDto.PostListDto> myPosts(Pageable pageable) {
        User user = userService.getCurrentUser();
        Page<Post> myPostList = postRepository.findByUser(user, pageable);

        Page<PostResponseDto.PostListDto> myPostPage = myPostList.map(
                myPost -> {
                    Boolean savedPost = savedPostService.isSavedPost(myPost, user);
                    Boolean likeStatus = postLikeService.likeStatus(myPost, user);
                    Boolean isUserPhoto = userPhotoService.isUserPhoto(myPost, user);
                    PostResponseDto.PostListDto postListDto = postConverter.toPostList(myPost, isUserPhoto,  savedPost, likeStatus);

                    return postListDto;
                });
        return myPostPage;

    }

    @Override
    public Page<PostResponseDto.PostListDto> boughtPhoto(Pageable pageable) {
        User user = userService.getCurrentUser();
        Page<UserPhoto> userPhotoList = userPhotoService.findUserPhotoByUser(user, pageable);

        Page<PostResponseDto.PostListDto> myPostPage = userPhotoList.map(
                myPost -> {
                    Boolean savedPost = savedPostService.isSavedPost(myPost.getPost(), user);
                    Boolean likeStatus = postLikeService.likeStatus(myPost.getPost(), user);
                    Boolean isUserPhoto = userPhotoService.isUserPhoto(myPost.getPost(), user);
                    PostResponseDto.PostListDto postListDto = postConverter.toPostList(myPost.getPost(), isUserPhoto,  savedPost, likeStatus);

                    return postListDto;
                });
        return myPostPage;
    }

    @Override
    public void sharePost(User sharedBy, List<User> shareTo, Long postId) {
        List<Notification> notifications = shareTo.stream().map(
                shareToUser -> postConverter.toShareNotification(sharedBy, shareToUser, postId)
        ).collect(Collectors.toList());

        notificationRepository.saveAll(notifications);
    }

    @Override
    public void updatePost(Long id, PostRequestDto.PostUpdateDto postUpdateDto) {
        postRepository.updatePost(id, postUpdateDto.getContent(), postUpdateDto.getIsSale(), postUpdateDto.getPoint());
        Post post = postRepository.findById(id).orElseThrow();
        List<PostBookmark> postBookmarkIdList = postBookmarkService.findPostBookmarks(post);
        postBookmarkService.deletePostBookmarks(postBookmarkIdList);
        postBookmarkService.createPostBookmarks(postUpdateDto.getBookmark(), post);
    }
}
