package shop.photolancer.photolancer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.photolancer.photolancer.domain.Contest;

public interface ContestRepository extends JpaRepository<Contest, Long> {

}
