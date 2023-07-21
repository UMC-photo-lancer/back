package shop.photolancer.photolancer.domain.enums;

public enum PaymentMethodType {
    KG("kg"),
    TOSS("toss"),
    KAKAO("kakao");

    private final String paymentMethod;

    PaymentMethodType(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
