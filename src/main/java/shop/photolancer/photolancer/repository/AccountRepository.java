package shop.photolancer.photolancer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.photolancer.photolancer.domain.Account;
import shop.photolancer.photolancer.domain.User;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByUser(User user);
}
