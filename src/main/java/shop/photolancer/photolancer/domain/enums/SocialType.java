package shop.photolancer.photolancer.domain.enums;

public enum SocialType {
    KAKAO("kakao"),
    NAVER("naver"),
    GOOGLE("google"),
    TWITTER("twitter"),
    APPLE("apple")
    ;

    private final String socailType;

    SocialType(String socailType) {
        this.socailType = socailType;
    }

}
