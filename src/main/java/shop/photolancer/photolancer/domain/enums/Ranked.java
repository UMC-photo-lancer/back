package shop.photolancer.photolancer.domain.enums;

public enum Ranked {
    FIRST("1"),
    SECOND("2"),
    THIRD("3");

    private final String ranked;
    Ranked(String ranked) {
        this.ranked = ranked;
    }
}
