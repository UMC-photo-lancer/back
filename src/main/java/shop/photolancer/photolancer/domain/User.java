package shop.photolancer.photolancer.domain;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import shop.photolancer.photolancer.domain.base.BaseEntity;
import shop.photolancer.photolancer.domain.enums.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String nickname;

    private String userId;

    @Column(unique = true)
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

//    private String refreshToken;

    private String socialId;

    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    @Enumerated(EnumType.STRING)
    private Purpose purpose;

    private String explane;

    @Column(columnDefinition = "Integer default 0")
    private Integer level;

    @Column(columnDefinition = "Boolean default false")
    private Boolean isPro;
    // 15레벨 이상일때 pro

    @Column(columnDefinition = "Integer default 0")
    private Integer point;

    @Column(columnDefinition = "Double default 0")
    private Double experience;

    private String profileUrl;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    private Integer num_following;

    private Integer num_follower;

    private Integer num_post;

    private Integer num_notification;

    private String title;
    public void updatePoint(Integer amount){
        if (this.point == null){
            this.point = 0;
        }
        this.point += amount;
    }

    public void updateNotification(){
        if (this.num_notification == null){
            this.num_notification = 0;
        }
        this.num_notification += 1;
    }

    public void passwordEncode(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(this.password);
    }

    public void updateNum_following(Boolean isFollow){
        if (isFollow == true){
            this.num_following += 1;
        } else {
            this.num_following -= 1;
        }
    }

    public void updateNum_follower(Boolean isFollow){
        if (isFollow == true){
            this.num_follower += 1;
        }{
            this.num_follower -= 1;
        }
    }

    public void updateNum_post(){
        this.num_post += 1;
    }

    public void deactivateUser(){
        this.status = UserStatus.INACTIVE;
    }

    public void updateUser(String nickname, String explane, Purpose purpose){
        this.nickname = nickname;
        this.explane = explane;
        this.purpose = purpose;
        this.role = Role.USER;
    }

    public void updatePassword(String password){
        this.password = password;
    }

    public void updateProfileUrl(String profileUrl){
        this.profileUrl = profileUrl;
    }

//    public void updateRefreshToken(String updateRefreshToken) {
//        this.refreshToken = updateRefreshToken;
//    }
}
