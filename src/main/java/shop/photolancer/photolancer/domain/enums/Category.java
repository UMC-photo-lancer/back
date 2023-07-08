package shop.photolancer.photolancer.domain.enums;

public enum Category {
    SYSTEM("system"),
    EVENT("event"),
    SERVICE("service");

    private final String category;
    Category(String category) { this.category = category; }
}
