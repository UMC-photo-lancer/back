package shop.photolancer.photolancer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.photolancer.photolancer.domain.Charge;
import shop.photolancer.photolancer.domain.User;

import java.util.List;

public interface ChargeRepository extends JpaRepository<Charge, Long> {
    List<Charge> findAllByUserOrderByCreatedAtDesc(User user);
}
