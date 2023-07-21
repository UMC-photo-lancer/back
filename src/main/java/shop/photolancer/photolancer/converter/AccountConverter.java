package shop.photolancer.photolancer.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import shop.photolancer.photolancer.domain.Account;
import shop.photolancer.photolancer.domain.Charge;
import shop.photolancer.photolancer.domain.User;
import shop.photolancer.photolancer.domain.enums.NoteType;
import shop.photolancer.photolancer.domain.enums.PaymentMethodType;

@RequiredArgsConstructor
@Component
public class AccountConverter {
    public Account toAccount(User user, String bank, String accountNumber, Boolean isMain){
        return Account.builder()
                .user(user)
                .bank(bank)
                .accountNumber(accountNumber)
                .isMain(isMain)
                .build();
    }
}
