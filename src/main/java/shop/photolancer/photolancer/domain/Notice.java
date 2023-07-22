package shop.photolancer.photolancer.domain;

import lombok.*;
import shop.photolancer.photolancer.domain.base.BaseEntity;
import shop.photolancer.photolancer.domain.enums.Category;
import shop.photolancer.photolancer.domain.mapping.NoticeFile;
import shop.photolancer.photolancer.domain.mapping.PostImage;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Notice extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private Boolean isPublic;
}
