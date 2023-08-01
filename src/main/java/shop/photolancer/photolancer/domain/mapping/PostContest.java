package shop.photolancer.photolancer.domain.mapping;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import shop.photolancer.photolancer.domain.Contest;
import shop.photolancer.photolancer.domain.Post;
import shop.photolancer.photolancer.domain.enums.Ranked;

import javax.persistence.*;

@Entity
@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostContest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "contest_id")
    private Contest contest;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "post_id")
    private Post post;

    @Enumerated(EnumType.STRING)
    private Ranked ranked;
}
