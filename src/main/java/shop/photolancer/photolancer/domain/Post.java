package shop.photolancer.photolancer.domain;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import shop.photolancer.photolancer.domain.base.BaseEntity;


import javax.persistence.*;


@Entity
@Builder
@Getter
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
}
