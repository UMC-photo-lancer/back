package shop.photolancer.photolancer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.photolancer.photolancer.domain.Test;

public interface TestRepository extends JpaRepository<Test, Long> {
}
