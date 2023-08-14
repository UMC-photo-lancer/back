package shop.photolancer.photolancer.domain;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import shop.photolancer.photolancer.domain.base.BaseEntity;
import shop.photolancer.photolancer.domain.enums.PostStatus;
import shop.photolancer.photolancer.domain.mapping.*;

import javax.persistence.*;
import java.util.List;


@Entity
@Builder
@Getter
@DynamicInsert
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(columnDefinition = "TEXT")
    private String content;

    @ColumnDefault("0")
    private Integer likeCount;

    private Boolean isSale;

    private Integer point;

    private String thumbNailUri;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<PostImage> images;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<PostBookmark> bookmarks;

    @Enumerated(EnumType.STRING)
    private PostStatus postStatus;
}
