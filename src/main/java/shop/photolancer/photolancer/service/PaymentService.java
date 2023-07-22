package shop.photolancer.photolancer.service;

import shop.photolancer.photolancer.domain.Charge;
import shop.photolancer.photolancer.domain.User;
import shop.photolancer.photolancer.web.dto.PaymentResponseDto;

import java.util.List;

public interface PaymentService {

    Charge charge(User user, Integer amount, String paymentMethod);

    List<Charge> getAllCharges(User user);

    List<PaymentResponseDto.ExchangeDto> getExchange(User user);

    void exchange(User user, String bank, String accountNumber, Integer point);

    PaymentResponseDto.PurchaseDto getPurchase(Long postId, User user);

    void purchase(Long photoId, User user);

}
