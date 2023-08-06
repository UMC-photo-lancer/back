package shop.photolancer.photolancer.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import shop.photolancer.photolancer.converter.NoticeConverter;
import shop.photolancer.photolancer.converter.NoticeFileConverter;
import shop.photolancer.photolancer.domain.Notice;
import shop.photolancer.photolancer.domain.User;
import shop.photolancer.photolancer.domain.enums.Category;
import shop.photolancer.photolancer.domain.mapping.NoticeFile;
import shop.photolancer.photolancer.repository.NoticeFileRepository;
import shop.photolancer.photolancer.repository.NoticeRepository;
import shop.photolancer.photolancer.service.NoticeService;
import shop.photolancer.photolancer.web.dto.NoticeResponseDto;


import java.util.List;

@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService {
    private final NoticeRepository noticeRepository;
    private final NoticeConverter noticeConverter;
    private final NoticeFileRepository noticeFileRepository;
    private final NoticeFileConverter noticeFileConverter;
    @Override
    public Long upload(String content, String title, Category category,
                       Boolean isPublic, List<String> filePaths, User user) {
        Notice notice = noticeConverter.toNotice(content, title, category, isPublic, user);
        noticeRepository.save(notice);

        for (String fileUrl : filePaths) {
            NoticeFile noticeFile = noticeFileConverter.toEntity(fileUrl, notice);
            noticeFileRepository.save(noticeFile);
        }
        return null;
    }

    @Override
    public Long upload(String content, String title, Category category,
                       Boolean isPublic, User user) {
        Notice notice = noticeConverter.toNotice(content, title, category, isPublic, user);
        noticeRepository.save(notice);

        return null;
    }

    @Override
    public Page<NoticeResponseDto.NoticePagingDto> noticePage(Pageable request) {
        Page<Notice> noticeList = noticeRepository.findAll(request);

        Page<NoticeResponseDto.NoticePagingDto> noticePage = noticeList.map(
                notice -> noticeConverter.toNoticePage(
                                notice.getId(),
                                notice.getTitle(),
                                notice.getCreatedAt(),
                                notice.getCategory()
                        ));
        return noticePage;
    }

    @Override
    public Page<NoticeResponseDto.NoticePagingDto> noticePageCategory(Category category, Pageable request) {
        Page<Notice> noticeList = noticeRepository.findAllByCategory(category, request);
        Page<NoticeResponseDto.NoticePagingDto> noticeCategoryPage = noticeList.map(
                notice -> noticeConverter.toNoticePage(
                        notice.getId(),
                        notice.getTitle(),
                        notice.getCreatedAt(),
                        notice.getCategory()
                ));
        return noticeCategoryPage;
    }

    @Override
    public Notice findNoticeById(Long noticeId) {
        Notice notice = noticeRepository.findById(noticeId).orElseThrow();
        return notice;
    }

    public List<NoticeResponseDto.NoticeFileDto> findNoticeFileByNotice(Notice notice) {
        List<NoticeFile> noticeFiles = noticeFileRepository.findByNotice(notice);
        List<NoticeResponseDto.NoticeFileDto> noticeFileDtos = noticeFiles.stream().map(
                noticeFile -> noticeConverter.toNoticeFile(noticeFile)
        ).toList();
        return noticeFileDtos;
    }

}
