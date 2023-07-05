package shop.photolancer.photolancer.domain.mapping;

import lombok.*;
import shop.photolancer.photolancer.domain.Post;
import shop.photolancer.photolancer.domain.User;
import shop.photolancer.photolancer.domain.base.BaseEntity;

import javax.persistence.*;

@Entity
@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserPhoto extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "post_id")
    private Post post;
}
