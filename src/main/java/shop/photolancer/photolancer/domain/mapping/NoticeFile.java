package shop.photolancer.photolancer.domain.mapping;

import lombok.*;
import shop.photolancer.photolancer.domain.Notice;
import shop.photolancer.photolancer.domain.enums.Category;

import javax.persistence.*;

@Entity
@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NoticeFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String uri;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "notice_id")
    private Notice notice;
    
}

