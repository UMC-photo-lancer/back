package shop.photolancer.photolancer.domain.enums;

public enum NotificationType {
    POINT("point"),
    FOLLOW("follow"),
    SYSTEM("system"),
    SELL("sell"),
    HEART("heart"),
    // 공감
    ALL("all"),
    SHARE("share");
    // 게시글 공유
    private final String notification;

    NotificationType(String notification) {
        this.notification = notification;
    }

    public String getNotification() {
        return notification;
    }

    public static NotificationType fromString(String notification) {
        for (NotificationType type : NotificationType.values()) {
            if (type.getNotification().equalsIgnoreCase(notification)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid Notification Type: " + notification);
    }

}
