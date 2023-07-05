package shop.photolancer.photolancer.domain.enums;

public enum Purpose {
    HOBBY("hobby"),
    BUSINESS("business"),
    GENERAL("general");
    private final String purpose;

    Purpose(String purpose) {
        this.purpose = purpose;
    }

}
