package shop.photolancer.photolancer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.photolancer.photolancer.domain.Contest;
import shop.photolancer.photolancer.domain.enums.Ranked;
import shop.photolancer.photolancer.domain.mapping.PostContest;

import java.util.List;

public interface PostContestRepository extends JpaRepository<PostContest, Long> {
    List<PostContest> findByContestAndRankedIn(Contest contest, List<Ranked> rankedList);
}
