package shop.photolancer.photolancer.config.Login;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import shop.photolancer.photolancer.config.oauth2.handler.OAuth2LoginFailureHandler;
import shop.photolancer.photolancer.config.oauth2.handler.OAuth2LoginSuccessHandler;
import shop.photolancer.photolancer.config.oauth2.service.CustomOAuth2UserService;
import shop.photolancer.photolancer.service.impl.UserServiceImpl;

import java.util.Arrays;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class AuthenticationConfig {
    private final UserServiceImpl userService;
    private final PasswordEncoder passwordEncoder;
    private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;
    private final OAuth2LoginFailureHandler oAuth2LoginFailureHandler;
    private final CustomOAuth2UserService customOAuth2UserService;

    @Value("${jwt.secret}")
    private String secretKey;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .httpBasic().disable() // 인증을 ui가 아닌 token으로 할꺼기 때문
                .csrf().disable()
                .cors().configurationSource(request -> {
                    var cors = new CorsConfiguration();
                    cors.setAllowedOriginPatterns(Arrays.asList("*"));
                    cors.setAllowedMethods(Arrays.asList("GET","POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
                    cors.setAllowedHeaders(Arrays.asList("*"));
                    cors.setAllowCredentials(true);
                    return cors;
                })
                .and()
                .authorizeRequests()
                .antMatchers("/api/v1/users/join","/api/v1/users/login","/api/v1/users/find-id","/api/v1/users/find-pw","/api/v1/users/find-id-mail").permitAll() // join과 login은 언제나 가능해야 하기 때문
                .antMatchers(HttpMethod.POST,"/api/v1/**").authenticated() // 위의 두가지를 제외한 모든 포스트 요청을 인증필요로 막아놓음, 두번째 파라미터에 api작성가능
//                .requestMatchers("/api/v1/users/password").authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // jwt사용하는 경우 사용함
                .and()
                .oauth2Login()
                .successHandler(oAuth2LoginSuccessHandler) // 동의하고 계속하기를 눌렀을 때 Handler 설정
                .failureHandler(oAuth2LoginFailureHandler) // 소셜 로그인 실패 시 핸들러 설정
                .userInfoEndpoint()
                .userService(customOAuth2UserService); // customUserService 설정
//                .and()
        httpSecurity
                .addFilterBefore(new JwtFilter(userService,secretKey), UsernamePasswordAuthenticationFilter.class); // 받은 token을 푸려면 secret key가 있어야 하므로 secretkey를 넣어줌, JwtFilter를 UsernamePasswordAuthenticationFilter앞에다 넣는 거임

        return httpSecurity.build();
    }
}