package shop.photolancer.photolancer.domain.enums;

public enum PaymentMethodType {
    CARD("card");

    private final String paymentMethod;

    PaymentMethodType(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
