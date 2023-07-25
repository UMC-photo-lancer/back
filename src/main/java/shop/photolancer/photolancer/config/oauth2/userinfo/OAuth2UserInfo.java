package shop.photolancer.photolancer.config.oauth2.userinfo;

import java.util.Map;

public abstract class OAuth2UserInfo {
    // 소셜 타입별 유저 정보를 가지는 추상 클래스
    protected Map<String, Object> attributes;

    public OAuth2UserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public abstract String getId(); //소셜 식별 값 : 구글 - "sub", 카카오 - "id", 네이버 - "id"
    public abstract String getName();

}
