package shop.photolancer.photolancer.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import shop.photolancer.photolancer.domain.Charge;
import shop.photolancer.photolancer.domain.User;
import shop.photolancer.photolancer.domain.enums.ChargeStatus;
import shop.photolancer.photolancer.repository.UserRepository;

@RequiredArgsConstructor
@Component
public class PaymentConverter {

    private UserRepository userRepository;

    public Charge toCharge(User user, Integer amount, String paymentMethod) {
        return Charge.builder()
                .user(user)
                .amount(amount)
                .paymentMethod(paymentMethod)
                .status(ChargeStatus.COMPLETE)
                .build();
    }
}
