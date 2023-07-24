package shop.photolancer.photolancer.config.Login.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

@Slf4j
public class JwtUtil {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);
    // Token에서 userId꺼내기
    public static String  getName(String token, String secretKey) {
//        log.info("그럼 여기서 터지는거야 ?");
        Claims claims = Jwts.parser().setSigningKey(secretKey.getBytes()).parseClaimsJws(token).getBody();
        String userName = (String) claims.get("Name");
//        logger.info("User ID from token: " + userId);
        return userName;
    }

    //Token만료
    public static boolean isExpired(String token, String secretKey) {
//        log.info("들어왔니 ?");
//        log.info("secretkey:{}",secretKey.getBytes());
        return Jwts.parser().setSigningKey(secretKey.getBytes()).parseClaimsJws(token)
                .getBody().getExpiration().before(new Date());
    }


    public static String createJwt(String name, String secretKey, Long expireMs) {
        Claims claims = Jwts.claims(); // 일종의 map claims로 정보를 담는다
        claims.put("Name", name);
//        log.info("secretKey:{}",secretKey);
        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis())) // 시작시간
                .setExpiration(new Date(System.currentTimeMillis() + expireMs)) // 만료시간
                .signWith(SignatureAlgorithm.HS256, secretKey.getBytes()) // 어떤 알고리즘을 사용할지
                .compact();

//        logger.info("Generated JWT: {}", token);

        return token;
    }
}
