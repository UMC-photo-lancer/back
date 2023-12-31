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
import shop.photolancer.photolancer.web.dto.NoticeRequestDto;
import shop.photolancer.photolancer.web.dto.NoticeResponseDto;


import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService {
    private final NoticeRepository noticeRepository;
    private final NoticeConverter noticeConverter;
    private final NoticeFileRepository noticeFileRepository;
    private final NoticeFileConverter noticeFileConverter;
    private final NoticeFileServiceImpl noticeFileService;
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

    @Override
    public void deleteNotice(Long noticeId) {
        Notice notice = findNoticeById(noticeId);
        noticeFileService.deleteAWSFile(notice);
        noticeFileService.deleteFile(notice);
        noticeRepository.delete(notice);
    }

    @Override
    public void updateNotice(Long noticeId, NoticeRequestDto.NoticeUpdateDto request) {
        Notice notice = findNoticeById(noticeId);
        noticeRepository.updateNotice(noticeId, request.getCategory(), request.getIsPublic(), request.getContent(), request.getTitle());
        noticeFileService.deleteAWSFile(notice);
        noticeFileService.deleteFile(notice);
    }

    @Override
    public void updateNotice(Long noticeId, NoticeRequestDto.NoticeUpdateDto request, List<String> filePaths) {
        Notice notice = findNoticeById(noticeId);
        noticeRepository.updateNotice(noticeId, request.getCategory(), request.getIsPublic(), request.getContent(), request.getTitle());
        noticeFileService.deleteAWSFile(notice);
        noticeFileService.deleteFile(notice);

        for (String fileUrl : filePaths) {
            NoticeFile noticeFile = noticeFileConverter.toEntity(fileUrl, notice);
            noticeFileRepository.save(noticeFile);
        }
    }
}
