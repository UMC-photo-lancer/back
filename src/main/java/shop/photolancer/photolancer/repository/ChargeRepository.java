package shop.photolancer.photolancer.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import shop.photolancer.photolancer.domain.Charge;
import shop.photolancer.photolancer.domain.User;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface ChargeRepository extends JpaRepository<Charge, Long> {
    List<Charge> findAllByUserOrderByCreatedAtDesc(User user);

    Page<Charge>  findAllByUser(User user, Pageable pageable);
}
