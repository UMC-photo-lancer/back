package shop.photolancer.photolancer.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import shop.photolancer.photolancer.domain.ChatRoom;
import shop.photolancer.photolancer.domain.User;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    Page<ChatRoom> findAllBySenderOrReceiver(Pageable pageable, User sender, User receiver);

    ChatRoom findBySenderAndReceiver(User sender, User receiver);
    @Query("SELECT cr FROM ChatRoom cr WHERE " +
            "((cr.sender = :sender AND cr.receiver = :receiver) OR (cr.sender = :receiver AND cr.receiver = :sender)) " +
            "OR ((cr.sender = :receiver AND cr.receiver = :sender) OR (cr.sender = :sender AND cr.receiver = :receiver))")
    ChatRoom findBySenderOrReceiver(User sender, User receiver);
}
