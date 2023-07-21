package shop.photolancer.photolancer.domain.enums;

public enum NoteType {
    CHARGE("충전"),
    EXCHANGE("환전");

    private final String note;

    NoteType(String note) {
        this.note = note;
    }
}
