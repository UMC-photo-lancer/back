package shop.photolancer.photolancer.config.Login;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import shop.photolancer.photolancer.config.Login.utils.JwtUtil;
import shop.photolancer.photolancer.service.impl.UserServiceImpl;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {
    // 안보내는 요청에 대해서도 허용하면 안되므로, jwt를 요청이 올때마다 매번 인증해주는 방식
//    public JwtFilter(Object p0, Object p1) {
//    }
    private  final UserServiceImpl userService;
    private final String secretKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 잘 보내졌는지 확인
        final String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        log.info("authorization:{}",authorization);

        // token 안보내면 block
        if(authorization == null || !authorization.startsWith(("Bearer"))) {
            // authentication을 안보냈을 경우
            log.error("authorization을 잘못 보냈습니다.");
            filterChain.doFilter(request,response);
            return;
        }

        // Token 꺼내기
        String token = authorization.split(" ")[1];

        // Token expire여부
        if(JwtUtil.isExpired(token, secretKey)){
            log.error("Token이 만료되었습니다.");
            filterChain.doFilter(request,response);
            return;
        }
        // userId 을 token에서 꺼내기
        String userName = JwtUtil.getName(token,secretKey);
        log.info("jwt 이상없음");

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userName, null, List.of(new SimpleGrantedAuthority("USER")));
        // Role이 DB에 있을경우 필요함

        // Detail을 넣어줍니다.
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request,response);
    }
}
