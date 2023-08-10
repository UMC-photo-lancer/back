package shop.photolancer.photolancer.config.oauth2;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import shop.photolancer.photolancer.domain.enums.Role;

import java.util.Collection;
import java.util.Map;

@Getter
public class CustomOAuth2User extends DefaultOAuth2User {
    // Resource Server에서 제공하지 않는 추가 정보들을 내 서비스에서 가지고 있기 위해 구현
    private String email;
    private Role role;
    private String password;
    private String social;
    public CustomOAuth2User(Collection<? extends GrantedAuthority> authorities,
                            Map<String, Object> attributes, String nameAttributeKey,
                            String email, Role role, String password) {
        super(authorities, attributes, nameAttributeKey);
        this.email = email;
        this.role = role;
        this.password = password;
        // super로 부모객체 DefulatOAuth2User생성후, email과 role을 추가받아 주입함
    }
}
