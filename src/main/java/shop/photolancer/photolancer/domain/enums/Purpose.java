package shop.photolancer.photolancer.domain.enums;

public enum Purpose {
    HOBBY("hobby"),
    BUSINESS("business");
    private final String purpose;

    Purpose(String purpose) {
        this.purpose = purpose;
    }

    public static Purpose fromStringIgnoreCase(String value) {
        for (Purpose purpose : Purpose.values()) {
            if (purpose.purpose.equalsIgnoreCase(value)) {
                return purpose;
            }
        }
        throw new IllegalArgumentException("No enum constant " + Purpose.class.getCanonicalName() + "." + value);
    }

}
