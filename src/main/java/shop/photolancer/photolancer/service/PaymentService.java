package shop.photolancer.photolancer.service;

import shop.photolancer.photolancer.domain.Charge;
import shop.photolancer.photolancer.domain.User;
import shop.photolancer.photolancer.web.dto.PaymentResponseDto;

public interface PaymentService {

    Charge charge(User user, Integer amount, String paymentMethod);

}
