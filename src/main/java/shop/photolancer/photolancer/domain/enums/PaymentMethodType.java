package shop.photolancer.photolancer.domain.enums;

public enum PaymentMethodType {
    CARD("card"),
    TOSS("toss"),
    KAKAO("kakao");

    private final String paymentMethod;

    PaymentMethodType(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
