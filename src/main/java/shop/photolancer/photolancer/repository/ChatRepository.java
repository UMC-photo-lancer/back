package shop.photolancer.photolancer.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import shop.photolancer.photolancer.domain.ChatRoom;
import shop.photolancer.photolancer.domain.User;

public interface ChatRepository extends JpaRepository<ChatRoom, Long> {

    Page<ChatRoom> findAllBySenderOrReceiver(Pageable pageable, User sender, User receiver);
}
