package shop.photolancer.photolancer.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.photolancer.photolancer.converter.NoticeConverter;
import shop.photolancer.photolancer.converter.NoticeFileConverter;
import shop.photolancer.photolancer.domain.Notice;
import shop.photolancer.photolancer.domain.enums.Category;
import shop.photolancer.photolancer.domain.mapping.NoticeFile;
import shop.photolancer.photolancer.repository.NoticeFileRepository;
import shop.photolancer.photolancer.repository.NoticeRepository;
import shop.photolancer.photolancer.service.NoticeService;

import java.util.ArrayList;
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
                       Boolean isPublic, List<String> filePaths) {
        Notice notice = noticeConverter.toNotice(content, title, category, isPublic);
        noticeRepository.save(notice);

        for (String fileUrl : filePaths) {
            NoticeFile noticeFile = noticeFileConverter.toEntity(fileUrl, notice);
            noticeFileRepository.save(noticeFile);
        }

        return null;
    }
}
