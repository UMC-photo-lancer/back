package shop.photolancer.photolancer.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import shop.photolancer.photolancer.converter.PostConverter;
import shop.photolancer.photolancer.domain.Post;
import shop.photolancer.photolancer.domain.User;
import shop.photolancer.photolancer.domain.mapping.UserPhoto;
import shop.photolancer.photolancer.repository.PostBookmarkRepository;
import shop.photolancer.photolancer.repository.UserPhotoRepository;
import shop.photolancer.photolancer.service.PostBookmarkService;
import shop.photolancer.photolancer.web.dto.BookmarkDto;
import shop.photolancer.photolancer.web.dto.PostResponseDto;


@Service
@RequiredArgsConstructor
public class PostBookmarkServiceImpl implements PostBookmarkService {
    private final PostBookmarkRepository postBookmarkRepository;
    public final UserServiceImpl userService;
    public final UserBookmarkServiceImpl userBookmarkService;
    public final UserPhotoRepository userPhotoRepository;
    public final PostConverter postConverter;
    public Page<PostResponseDto.PostListDto> postBookmarkDefaultList(Pageable request) {

        User user = userService.getCurrentUser();
        BookmarkDto usersFirstBookmark = userBookmarkService.findBookmarksByUser(user).get(0);
        String content = usersFirstBookmark.getContent().toString();
        String cleanContent = content.replace("[", "").replace("]", "");
        Page<Post> postList = postBookmarkRepository.findByPostBookmarkName(request, cleanContent);

        Page<PostResponseDto.PostListDto> postBookmarkList = postList.map(
                hotPhoto -> {
                    UserPhoto userPhoto = userPhotoRepository.findByUserAndPost(user, hotPhoto);
                    if (userPhoto == null) {
                        PostResponseDto.PostListDto postListDto = postConverter.toPostList(hotPhoto, false);
                        return postListDto;
                    }
                    PostResponseDto.PostListDto postListDto = postConverter.toPostList(hotPhoto, true);

                    return postListDto;
                });
        return postBookmarkList;
    }

    public Page<PostResponseDto.PostListDto> postBookmarkList(Pageable request, String bookmarkName) {

        User user = userService.getCurrentUser();
        Page<Post> postList = postBookmarkRepository.findByPostBookmarkName(request, bookmarkName);

        Page<PostResponseDto.PostListDto> postBookmarkList = postList.map(
                hotPhoto -> {
                    UserPhoto userPhoto = userPhotoRepository.findByUserAndPost(user, hotPhoto);
                    if (userPhoto == null) {
                        PostResponseDto.PostListDto postListDto = postConverter.toPostList(hotPhoto, false);
                        return postListDto;
                    }
                    PostResponseDto.PostListDto postListDto = postConverter.toPostList(hotPhoto, true);

                    return postListDto;
                });
        return postBookmarkList;
    }

}
