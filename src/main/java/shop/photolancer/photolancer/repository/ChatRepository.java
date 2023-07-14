package shop.photolancer.photolancer.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import shop.photolancer.photolancer.domain.Chat;
import shop.photolancer.photolancer.domain.User;

public interface ChatRepository extends JpaRepository<Chat, Long> {

    Page<Chat> findAllBySenderOrReceiver(Pageable pageable, User sender, User receiver);
}
