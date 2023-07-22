package shop.photolancer.photolancer.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import shop.photolancer.photolancer.domain.Account;
import shop.photolancer.photolancer.domain.User;
import shop.photolancer.photolancer.web.dto.PaymentResponseDto;

@RequiredArgsConstructor
@Component
public class AccountConverter {
    public Account toAccount(User user, String bank, String accountNumber){
        return Account.builder()
                .user(user)
                .bank(bank)
                .accountNumber(accountNumber)
                .isMain(false)
                .build();
    }

    public PaymentResponseDto.ExchangeDto toExchange(Long id, String bank, String accountNumber, Boolean isMain){
        return PaymentResponseDto.ExchangeDto.builder()
                .id(id)
                .bank(bank)
                .accountNumber(accountNumber)
                .isMain(isMain)
                .build();
    }
}
