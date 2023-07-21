package shop.photolancer.photolancer.domain.mapping;

import lombok.*;
import shop.photolancer.photolancer.domain.Bookmark;
import shop.photolancer.photolancer.domain.Post;

import javax.persistence.*;

@Entity
@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostBookmark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "bookmark_id")
    private Bookmark bookmark;

    public PostBookmark(Post post, Bookmark bookmark) {
        this.post = post;
        this.bookmark = bookmark;
    }
}
