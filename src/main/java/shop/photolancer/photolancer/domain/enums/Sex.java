package shop.photolancer.photolancer.domain.enums;

public enum Sex {
    MALE("male"),
    FEMALE("female"),
    NONE("none");
    private final String sex;

    Sex(String sex) {
        this.sex = sex;
    }

}
