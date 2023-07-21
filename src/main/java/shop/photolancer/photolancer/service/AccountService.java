package shop.photolancer.photolancer.service;

import shop.photolancer.photolancer.domain.User;

public interface AccountService {

    void add(User user, String bank, String accountNumber);

    void updateIsMain(User user, Long accountId);

}
