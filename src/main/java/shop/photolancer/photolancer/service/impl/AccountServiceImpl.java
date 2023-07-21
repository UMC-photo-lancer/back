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

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    public final AccountRepository accountRepository;
    public final AccountConverter accountConverter;

    @Transactional
    @Override
    public void add(User user, String bank, String accountNumber){

        Account account = accountConverter.toAccount(user, bank, accountNumber);

        accountRepository.save(account);
    }

    @Transactional
    @Override
    public void updateIsMain(User user, Long accountId){
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new NoSuchElementException("Account not found."));

        List<Account> userAccounts = accountRepository.findByUser(user);

        for (Account userAccount : userAccounts) {
            userAccount.setIsMain(false);
        }

        account.setIsMain(true);

        accountRepository.saveAll(userAccounts);
    }
}
