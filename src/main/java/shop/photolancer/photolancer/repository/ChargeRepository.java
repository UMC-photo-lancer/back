package shop.photolancer.photolancer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.photolancer.photolancer.domain.Charge;

public interface ChargeRepository extends JpaRepository<Charge, Long> {
}
