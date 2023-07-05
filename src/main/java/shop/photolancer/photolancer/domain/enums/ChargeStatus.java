package shop.photolancer.photolancer.domain.enums;

import lombok.Getter;

@Getter
public enum  ChargeStatus {
    COMPLETE("complete");
    private final String statusName;

    ChargeStatus(String statusName) {
        this.statusName = statusName;
    }
}
