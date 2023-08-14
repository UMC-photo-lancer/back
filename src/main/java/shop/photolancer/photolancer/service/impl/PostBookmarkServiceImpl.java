package shop.photolancer.photolancer.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import shop.photolancer.photolancer.converter.BookmarkConverter;
import shop.photolancer.photolancer.converter.PostConverter;
import shop.photolancer.photolancer.domain.Bookmark;
import shop.photolancer.photolancer.domain.Post;
import shop.photolancer.photolancer.domain.User;
import shop.photolancer.photolancer.domain.mapping.PostBookmark;
import shop.photolancer.photolancer.repository.PostBookmarkRepository;
import shop.photolancer.photolancer.service.PostBookmarkService;
import shop.photolancer.photolancer.web.dto.BookmarkDto;
import shop.photolancer.photolancer.web.dto.BookmarkResponseDto;
import shop.photolancer.photolancer.web.dto.PostResponseDto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


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
    private final BookmarkConverter bookmarkConverter;

    public Page<PostResponseDto.PostListDto> postBookmarkDefaultList(Pageable request) {

        User user = userService.getCurrentUser();
        BookmarkDto usersFirstBookmark = userBookmarkService.findBookmarksByUser(user).get(0);
        String content = usersFirstBookmark.getContent().toString();
        String cleanContent = content.replace("[", "").replace("]", "");
        Page<Post> postList = postBookmarkRepository.findByPostBookmarkNameNotDeleted(request, cleanContent);

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
        Page<Post> postList = postBookmarkRepository.findByPostBookmarkNameNotDeleted(request, bookmarkName);

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

    // postId로 bookmark 이름 반환
    public List<PostBookmark> findByPostIdWithBookmark(Long postId) {
        return postBookmarkRepository.findByPostIdWithBookmark(postId);
    }

    // boookmark nameList로 반환
    public List<String> toPostBookmarkNameList(List<PostBookmark> postBookmarkList) {
        List<String> postBookmarkNameList = new ArrayList<>();

        for (PostBookmark p : postBookmarkList) {
            postBookmarkNameList.add(p.getBookmark().getName());
        }
        return postBookmarkNameList;
    }

    // bookmark 전체 반환, 게시글 수와 함께 반환
    public List<BookmarkResponseDto.BookmarkDataResponseDto> getBookmarkData () {
        List<Bookmark> bookmarks = bookmarkService.findAllBookmark();
        List<BookmarkResponseDto.BookmarkDataResponseDto> bookmarkDataList = bookmarks.stream().map(
                bookmark -> {
                    Long postNum = postBookmarkRepository.countByBookmark(bookmark);
                    return bookmarkConverter.toBookmarkData(bookmark, postNum);
                }
        ).collect(Collectors.toList());
        return bookmarkDataList;
    }
}
