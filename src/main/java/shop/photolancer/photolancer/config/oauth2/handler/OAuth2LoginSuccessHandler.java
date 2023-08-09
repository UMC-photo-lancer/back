package shop.photolancer.photolancer.config.oauth2.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import shop.photolancer.photolancer.config.Login.utils.JwtUtil;
import shop.photolancer.photolancer.config.oauth2.CustomOAuth2User;
import shop.photolancer.photolancer.domain.User;
import shop.photolancer.photolancer.domain.enums.Role;
import shop.photolancer.photolancer.repository.UserRepository;
import shop.photolancer.photolancer.service.impl.UserServiceImpl;
import shop.photolancer.photolancer.web.dto.LoginResponseDto;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

//    private final JwtService jwtService;
    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expireMs}")
    private Long expireMs;

    private final UserRepository userRepository;
    private final UserServiceImpl userService;
    private final ObjectMapper objectMapper = new ObjectMapper(); // Jackson ObjectMapper


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("OAuth2 Login 성공!");
        try {
            CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();


            // User의 Role이 GUEST일 경우 처음 요청한 회원이므로 회원가입 페이지로 리다이렉트
            if(oAuth2User.getRole() == Role.GUEST) {
                loginSuccess(response, oAuth2User,Role.GUEST);
//                String userName = oAuth2User.getName();
//
//                response.addHeader("Authorization", JwtUtil.createJwt(userName, secretKey,expireMs));
//                response.sendRedirect("api/v1/users/join"); // 프론트의 회원가입 추가 정보 입력 폼으로 리다이렉트
            } else {
                loginSuccess(response, oAuth2User,oAuth2User.getRole()); // 로그인에 성공한 경우 access, refresh 토큰 생성
            }
        } catch (Exception e) {
            throw e;
        }
    }

    // TODO : 소셜 로그인 시에도 무조건 토큰 생성하지 말고 JWT 인증 필터처럼 RefreshToken 유/무에 따라 다르게 처리해보기
    private void loginSuccess(HttpServletResponse response, CustomOAuth2User oAuth2User,Role role) throws IOException {
//        String accessToken = jwtService.createAccessToken(oAuth2User.getEmail());
//        String refreshToken = jwtService.createRefreshToken();
//        response.addHeader(jwtService.getAccessHeader(), "Bearer " + accessToken);
//        response.addHeader(jwtService.getRefreshHeader(), "Bearer " + refreshToken);
//
//        jwtService.sendAccessAndRefreshToken(response, accessToken, refreshToken);
//        jwtService.updateRefreshToken(oAuth2User.getEmail(), refreshToken);

        /*
        String userName = oAuth2User.getName();
        response.addHeader("Authorization", JwtUtil.createJwt(userName, secretKey,expireMs));
        log.info(JwtUtil.createJwt(userName, secretKey,expireMs));
        */

        String jwt = JwtUtil.createJwt(oAuth2User.getName(), secretKey, expireMs);
        User currentUser = userService.getCurrentUser();

        if (role == Role.GUEST) {
            // GUEST의 경우 로직
            LoginResponseDto responseDto = new LoginResponseDto(currentUser, jwt, "사용자 정보 등록이 필요합니다.");

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(responseDto));
            response.getWriter().flush();

            // 필요하면 리다이렉트도 추가할 수 있습니다.
            // response.sendRedirect("api/v1/users/join");
        } else {
            // 다른 Role의 경우 로직
            LoginResponseDto responseDto = new LoginResponseDto(currentUser, jwt, null); // null이 아닌 다른 메시지로 변경 가능

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(responseDto));
            response.getWriter().flush();
        }
    }
}