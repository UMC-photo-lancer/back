package shop.photolancer.photolancer.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.photolancer.photolancer.converter.AccountConverter;
import shop.photolancer.photolancer.domain.Account;
import shop.photolancer.photolancer.domain.Charge;
import shop.photolancer.photolancer.domain.User;
import shop.photolancer.photolancer.repository.AccountRepository;
import shop.photolancer.photolancer.service.AccountService;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    public final AccountRepository accountRepository;
    public final AccountConverter accountConverter;

    @Transactional
    @Override
    public void add(User user, String bank, String accountNumber){
        Account existAccount = accountRepository.findByUser(user);

        Boolean isMain = false;

        if (existAccount == null){
            isMain = true;
        }

        Account account = accountConverter.toAccount(user, bank, accountNumber, isMain);

        accountRepository.save(account);
    }
}
