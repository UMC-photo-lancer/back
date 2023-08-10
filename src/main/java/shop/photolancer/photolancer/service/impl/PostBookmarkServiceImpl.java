package shop.photolancer.photolancer.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import shop.photolancer.photolancer.converter.PostConverter;
import shop.photolancer.photolancer.domain.Bookmark;
import shop.photolancer.photolancer.domain.Post;
import shop.photolancer.photolancer.domain.User;
import shop.photolancer.photolancer.domain.mapping.PostBookmark;
import shop.photolancer.photolancer.repository.PostBookmarkRepository;
import shop.photolancer.photolancer.repository.UserPhotoRepository;
import shop.photolancer.photolancer.service.PostBookmarkService;
import shop.photolancer.photolancer.web.dto.BookmarkDto;
import shop.photolancer.photolancer.web.dto.PostResponseDto;

import java.util.List;


@Service
@RequiredArgsConstructor
public class PostBookmarkServiceImpl implements PostBookmarkService {
    private final PostBookmarkRepository postBookmarkRepository;
    private final UserServiceImpl userService;
    private final UserBookmarkServiceImpl userBookmarkService;
    private final PostConverter postConverter;
    private final PostLikeServiceImpl postLikeService;
    private final SavedPostServiceImpl savedPostService;
    private final UserPhotoServiceImpl userPhotoService;
    private final BookmarkServiceImpl bookmarkService;

    public Page<PostResponseDto.PostListDto> postBookmarkDefaultList(Pageable request) {

        User user = userService.getCurrentUser();
        BookmarkDto usersFirstBookmark = userBookmarkService.findBookmarksByUser(user).get(0);
        String content = usersFirstBookmark.getContent().toString();
        String cleanContent = content.replace("[", "").replace("]", "");
        Page<Post> postList = postBookmarkRepository.findByPostBookmarkName(request, cleanContent);

        Page<PostResponseDto.PostListDto> postBookmarkList = postList.map(
                hotPhoto -> {
                    Boolean savedPost = savedPostService.isSavedPost(hotPhoto, user);
                    Boolean likeStatus = postLikeService.likeStatus(hotPhoto, user);
                    Boolean isUserPhoto = userPhotoService.isUserPhoto(hotPhoto, user);
                    PostResponseDto.PostListDto postListDto = postConverter.toPostList(hotPhoto, isUserPhoto, savedPost, likeStatus);

                    return postListDto;
                });
        return postBookmarkList;
    }

    public Page<PostResponseDto.PostListDto> postBookmarkList(Pageable request, String bookmarkName) {

        User user = userService.getCurrentUser();
        Page<Post> postList = postBookmarkRepository.findByPostBookmarkName(request, bookmarkName);

        Page<PostResponseDto.PostListDto> postBookmarkList = postList.map(
                photo -> {
                    Boolean savedPost = savedPostService.isSavedPost(photo, user);
                    Boolean likeStatus = postLikeService.likeStatus(photo, user);
                    Boolean isUserPhoto = userPhotoService.isUserPhoto(photo, user);
                    PostResponseDto.PostListDto postListDto = postConverter.toPostList(photo, isUserPhoto, savedPost, likeStatus);

                    return postListDto;
                });
        return postBookmarkList;
    }

    public void deletePostBookmarks(List<PostBookmark> postBookmarks) {
        postBookmarkRepository.deleteAll(postBookmarks);
    }

    public List<PostBookmark> findPostBookmarks(Post post) {
        List<PostBookmark> postBookmarksId = postBookmarkRepository.findByPost(post);
        return postBookmarksId;
    }

    public void savePostBookmark(PostBookmark postBookmark) {
        postBookmarkRepository.save(postBookmark);
    }

    public void createPostBookmarks(List<String> bookmarks, Post post) {
        for (String bookmarkName : bookmarks) {
            Bookmark bookmark = bookmarkService.createBookmark(bookmarkName);
            PostBookmark postBookmark = postConverter.toPostBookmark(post, bookmark);
            savePostBookmark(postBookmark);
        }
    }

    public void createPostBookmark(String bookmarkName, Post post) {
            Bookmark bookmark = bookmarkService.createBookmark(bookmarkName);
            PostBookmark postBookmark = postConverter.toPostBookmark(post, bookmark);
            savePostBookmark(postBookmark);
    }

    public PostBookmark findByBookmarkNameAndPost(String bookmarkName, Post post) {
        Bookmark bookmark = bookmarkService.findBookmark(bookmarkName);
        return postBookmarkRepository.findByBookmarkAndPost(bookmark, post);
    }
}
