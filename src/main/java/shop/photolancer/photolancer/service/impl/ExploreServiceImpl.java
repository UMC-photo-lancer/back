package shop.photolancer.photolancer.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import shop.photolancer.photolancer.converter.PostConverter;
import shop.photolancer.photolancer.domain.Contest;
import shop.photolancer.photolancer.domain.Post;
import shop.photolancer.photolancer.domain.User;
import shop.photolancer.photolancer.domain.enums.Ranked;
import shop.photolancer.photolancer.domain.mapping.PostContest;
import shop.photolancer.photolancer.repository.*;
import shop.photolancer.photolancer.service.ExploreService;
import shop.photolancer.photolancer.web.dto.PostResponseDto;

import java.util.Arrays;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ExploreServiceImpl implements ExploreService {
    private final PostConverter postConverter;
    private final PostRepository postRepository;
    private final ContestRepository contestRepository;
    private final PostContestRepository postContestRepository;
    private final UserServiceImpl userService;
    private final SavedPostServiceImpl savedPostService;
    private final PostLikeServiceImpl postLikeService;
    private final UserPhotoServiceImpl userPhotoService;

    @Override
    public Page<PostResponseDto.PostListDto> hotPhoto(Pageable request) {
        Page<Post> postImageList = postRepository.findAll(request);
        User user = userService.getCurrentUser();

        Page<PostResponseDto.PostListDto> hotPhotoPage = postImageList.map(
                hotPhoto -> {
                    Boolean savedPost = savedPostService.isSavedPost(hotPhoto, user);
                    Boolean likeStatus = postLikeService.likeStatus(hotPhoto, user);
                    Boolean isUserPhoto = userPhotoService.isUserPhoto(hotPhoto, user);
                    PostResponseDto.PostListDto postListDto = postConverter.toPostList(hotPhoto, isUserPhoto,  savedPost, likeStatus);

                    return postListDto;
        });
        return hotPhotoPage;
    }

    @Override
    public Page<PostResponseDto.PostListDto> recentPhoto(Pageable request) {
        Page<Post> postImageList = postRepository.findAll(request);

        User user = userService.getCurrentUser();

        Page<PostResponseDto.PostListDto> recentPhotoPage = postImageList.map(
                recentPhoto -> {
                    Boolean savedPost = savedPostService.isSavedPost(recentPhoto, user);
                    Boolean likeStatus = postLikeService.likeStatus(recentPhoto, user);
                    Boolean isUserPhoto = userPhotoService.isUserPhoto(recentPhoto, user);
                    PostResponseDto.PostListDto postListDto = postConverter.toPostList(recentPhoto, isUserPhoto, savedPost, likeStatus);

                    return postListDto;
                });
        return recentPhotoPage;
    }

     // 대회 get요청
    @Override
    public PostResponseDto.PostAwardsDto photoAwards() {
        Contest contest = contestRepository.findFirstByOrderByIdAsc();
        List<Contest> contestList = contestRepository.findAll();
        User user = userService.getCurrentUser();

        List<Ranked> ranked = Arrays.asList(Ranked.FIRST, Ranked.SECOND, Ranked.THIRD);
        List<PostContest> postContests = postContestRepository.findByContestAndRankedIn(contest, ranked);

        List<PostResponseDto.PostContestDto> postContestList = postContests.stream()
                .map(photo -> {
                    Boolean savedPost = savedPostService.isSavedPost(photo.getPost(), user);
                    Boolean likeStatus = postLikeService.likeStatus(photo.getPost(), user);
                    Boolean isUserPhoto = userPhotoService.isUserPhoto(photo.getPost(), user);

                    return postConverter.toPostContestDto(photo,postConverter.toPostList(photo.getPost(), isUserPhoto, savedPost, likeStatus));

                })
                .toList();

        return postConverter.toPostAwardsDto(contest, postContestList, contestList);
    }

    @Override
    public PostResponseDto.PostAwardsDto chagePhotoAward(Long id) {
        Contest contest = contestRepository.findById(id).orElseThrow();
        List<Contest> contestList = contestRepository.findAll();
        List<Ranked> ranked = Arrays.asList(Ranked.FIRST, Ranked.SECOND, Ranked.THIRD);
        List<PostContest> postContests = postContestRepository.findByContestAndRankedIn(contest, ranked);

        User user = userService.getCurrentUser();

        List<PostResponseDto.PostContestDto> postContestList = postContests.stream()
                .map(photo -> {
                    Boolean savedPost = savedPostService.isSavedPost(photo.getPost(), user);
                    Boolean likeStatus = postLikeService.likeStatus(photo.getPost(), user);
                    Boolean isUserPhoto = userPhotoService.isUserPhoto(photo.getPost(), user);

                    return postConverter.toPostContestDto(photo,postConverter.toPostList(photo.getPost(), isUserPhoto, savedPost, likeStatus));

                })
                .toList();
        return postConverter.toPostAwardsDto(contest, postContestList, contestList);
    }

    public PostResponseDto.PostExploreDto exploreDefault() {
        Pageable pageableRecent = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "createdAt"));
        Pageable pageableHot = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "likeCount"));
        List<PostResponseDto.PostListDto> hotPhotoPage = hotPhoto(pageableHot).getContent();
        List<PostResponseDto.PostListDto> recentPhotoPage = recentPhoto(pageableRecent).getContent();
        PostResponseDto.PostAwardsDto photoAward = photoAwards();

        return postConverter.toPostExploreDto(hotPhotoPage, recentPhotoPage, photoAward);
    }
}
