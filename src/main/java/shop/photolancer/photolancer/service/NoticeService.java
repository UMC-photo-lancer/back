package shop.photolancer.photolancer.service;

import shop.photolancer.photolancer.domain.enums.Category;
import shop.photolancer.photolancer.web.dto.NoticeRequestDto;

import java.util.List;

public interface NoticeService {
    Long upload(String content, String title, Category category, Boolean isPublic, List<String> filePaths);
}
