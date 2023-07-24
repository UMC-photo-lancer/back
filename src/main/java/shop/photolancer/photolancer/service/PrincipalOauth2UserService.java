package shop.photolancer.photolancer.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import shop.photolancer.photolancer.domain.User;
import shop.photolancer.photolancer.domain.enums.Purpose;
import shop.photolancer.photolancer.domain.enums.UserStatus;
import shop.photolancer.photolancer.repository.UserRepository;
import shop.photolancer.photolancer.service.impl.UserServiceImpl;

import javax.transaction.Transactional;
import java.util.Map;
import java.util.UUID;


@Transactional
@RequiredArgsConstructor
@Slf4j
@Service
public class PrincipalOauth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserServiceImpl userServiceImpl;
    private final UserRepository userRepository;
    protected final PasswordEncoder passwordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // Use the userRequest to get user info from OAuth2 server
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        /* OAuth2 서비스 id 구분코드 ( 구글, 카카오, 네이버 ) */
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        // Extract user info from OAuth2User
        /* OAuth2 로그인 진행시 키가 되는 필드 값 (PK) (구글의 기본 코드는 "sub") */
        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
        Map<String, Object> attributes = oAuth2User.getAttributes();
        String name = (String) attributes.get("name");
        String email = (String) attributes.get("email");
        String uuid = UUID.randomUUID().toString().substring(0, 6);
        String password = "google_password"+uuid;  // 사용자가 입력한 적은 없지만 만들어준다
        String userId = "google_id"+uuid;
        // 이미 가입한 유저인지 확인
        User user = userRepository.findByEmail(email);
        if (user == null) {
            // 만약, 가입되지 않다면 회원가입 진행
            user = User.builder()
                    .name(name)
                    .userId(userId)
                    .email(email)
                    .password(password)
                    .purpose(Purpose.HOBBY)
                    .status(UserStatus.ACTIVE)
                    .build();

            user.passwordEncode(passwordEncoder);
            userRepository.save(user);
        } else {
            userServiceImpl.login(user);
        }
        return null;
    }
}

//public class PrincipalOauth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
//
//    private final UserRepository userRepository;
//
//    @Override
//    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
//
//        /* OAuth2 서비스 id 구분코드 ( 구글, 카카오, 네이버 ) */
//        String registrationId = userRequest.getClientRegistration().getRegistrationId();
//
//        /* OAuth2 로그인 진행시 키가 되는 필드 값 (PK) (구글의 기본 코드는 "sub") */
//        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
//                        .getUserInfoEndpoint().getUserNameAttributeName();
//
//        /* OAuth2UserService */
//        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());
//
//        User user = saveOrUpdate(attributes);
//
//        return null;
//    }
//}
