package shop.photolancer.photolancer.domain;

import lombok.*;
import shop.photolancer.photolancer.domain.base.BaseEntity;
import shop.photolancer.photolancer.domain.enums.*;
import shop.photolancer.photolancer.web.dto.base.DefaultRes;

import javax.persistence.*;

@Entity
@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String nickname;

    @Column(unique = true)
    private String email;

    private String password;

    @Column(columnDefinition = "Boolean default false")
    private Boolean isAdmin;

    private String refreshToken;

    private Long socialId;

    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    @Enumerated(EnumType.STRING)
    private Purpose purpose;

    @Enumerated(EnumType.STRING)
    private Ability ability;

    @Enumerated(EnumType.STRING)
    private Sex sex;

    private String explane;

    @Column(columnDefinition = "Integer default 0")
    private Integer level;

    @Column(columnDefinition = "Boolean default false")
    private Boolean isPro;

    @Column(columnDefinition = "Integer default 0")
    private Integer point;

    @Column(columnDefinition = "Double default 0")
    private Double experience;

    private String profileUrl;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    public void updatePoint(Integer amount){
        this.point += amount;
    }

}
