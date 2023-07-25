package shop.photolancer.photolancer.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    USER("user"),
    GUEST("guest"),
    ADMIN("admin");

    private final String key;
}

