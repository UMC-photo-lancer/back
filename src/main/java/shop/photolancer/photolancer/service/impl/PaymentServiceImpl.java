package shop.photolancer.photolancer.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.photolancer.photolancer.converter.PaymentConverter;
import shop.photolancer.photolancer.domain.Charge;
import shop.photolancer.photolancer.domain.User;
import shop.photolancer.photolancer.repository.ChargeRepository;
import shop.photolancer.photolancer.service.PaymentService;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentConverter paymentConverter;
    private final ChargeRepository chargeRepository;

    @Override
    @Transactional
    public Charge charge(User user, Integer amount, String paymentMethod){
        Charge charge = paymentConverter.toCharge(user, amount, paymentMethod);
        return chargeRepository.save(charge);
    }

}
