package shop.photolancer.photolancer.domain.enums;

public enum NotificationType {
    POINT("포인트"),
    FOLLOW("팔로우"),
    //필요한 거 추가해주세용
    SHARE("게시글 공유");

    private final String notification;

    NotificationType(String notification) {
        this.notification = notification;
    }
}
