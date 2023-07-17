package shop.photolancer.photolancer.web.dto;

import lombok.Getter;
import lombok.Setter;

public class PaymentRequestDto {

    @Getter @Setter
    public static class ChargeDto{
        private Integer amount;
        private String  paymentMethod;
    }

}
