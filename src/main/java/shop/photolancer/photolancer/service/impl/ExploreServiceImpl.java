package shop.photolancer.photolancer.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import shop.photolancer.photolancer.converter.PostConverter;
import shop.photolancer.photolancer.domain.Contest;
import shop.photolancer.photolancer.domain.Post;
import shop.photolancer.photolancer.domain.User;
import shop.photolancer.photolancer.domain.enums.Ranked;
import shop.photolancer.photolancer.domain.mapping.PostContest;
import shop.photolancer.photolancer.domain.mapping.UserPhoto;
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
    private final UserPhotoRepository userPhotoRepository;
    private final UserServiceImpl userService;

    @Override
    public Page<PostResponseDto.PostListDto> hotPhoto(Pageable request) {
        Page<Post> postImageList = postRepository.findAll(request);

        User user = userService.getCurrentUser();

        Page<PostResponseDto.PostListDto> hotPhotoPage = postImageList.map(
                hotPhoto -> {
                    UserPhoto userPhoto = userPhotoRepository.findByUserAndPost(user, hotPhoto);
                    if (userPhoto == null) {
                        PostResponseDto.PostListDto postListDto = postConverter.toPostList(hotPhoto, false);
                        return postListDto;
                    }
                    PostResponseDto.PostListDto postListDto = postConverter.toPostList(hotPhoto, true);

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
                    UserPhoto userPhoto = userPhotoRepository.findByUserAndPost(user, recentPhoto);
                    if (userPhoto == null) {
                        PostResponseDto.PostListDto postListDto = postConverter.toPostList(recentPhoto, false);
                        return postListDto;
                    }
                    PostResponseDto.PostListDto postListDto = postConverter.toPostList(recentPhoto, true);

                    return postListDto;
                });
        return recentPhotoPage;
    }

     // 대회 get요청
    @Override
    public PostResponseDto.PostAwardsDto photoAwards() {
        Contest contest = contestRepository.findById(1L).orElseThrow();
        List<Contest> contestList = contestRepository.findAll();

        User user = userService.getCurrentUser();

        List<Ranked> ranked = Arrays.asList(Ranked.FIRST, Ranked.SECOND, Ranked.THIRD);
        List<PostContest> postContests = postContestRepository.findByContestAndRankedIn(contest, ranked);

        List<PostResponseDto.PostContestDto> postContestList = postContests.stream()
                .map(photo -> {
                    UserPhoto userPhoto = userPhotoRepository.findByUserAndPost(user, photo.getPost());

                    if (userPhoto == null) {
                        return postConverter.toPostContestDto(photo, postConverter.toPostList(photo.getPost(), false));
                    }

                    return postConverter.toPostContestDto(photo,postConverter.toPostList(photo.getPost(), true));

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
                    UserPhoto userPhoto = userPhotoRepository.findByUserAndPost(user, photo.getPost());

                    if (userPhoto == null) {
                        return postConverter.toPostContestDto(photo, postConverter.toPostList(photo.getPost(), false));
                    }

                    return postConverter.toPostContestDto(photo,postConverter.toPostList(photo.getPost(), true));

                })
                .toList();
        return postConverter.toPostAwardsDto(contest, postContestList, contestList);
    }
}
