package shop.photolancer.photolancer.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import shop.photolancer.photolancer.domain.Chat;
import shop.photolancer.photolancer.domain.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {
    Page<Message> findByChat(Pageable pageable, Chat chat);
}
