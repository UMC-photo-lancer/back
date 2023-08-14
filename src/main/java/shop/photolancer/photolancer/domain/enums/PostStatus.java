package shop.photolancer.photolancer.domain.enums;

public enum PostStatus {
    DELETED("Deleted"),
    EXISTENT("Existent");

    private final String statusName;

    PostStatus(String statusName) {
        this.statusName = statusName;
    }
}
