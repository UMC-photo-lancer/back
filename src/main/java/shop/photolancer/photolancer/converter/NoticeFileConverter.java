package shop.photolancer.photolancer.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import shop.photolancer.photolancer.domain.Notice;
import shop.photolancer.photolancer.domain.mapping.NoticeFile;

@RequiredArgsConstructor
@Component
public class NoticeFileConverter {
    public NoticeFile toEntity(String uri, Notice notice) {
        return NoticeFile.builder()
                .uri(uri)
                .notice(notice)
                .build();
    }
}
