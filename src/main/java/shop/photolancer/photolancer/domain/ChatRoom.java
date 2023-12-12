package shop.photolancer.photolancer.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "sender_id")
    private User sender;


    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "receiver_id")
    private User receiver;

    public static ChatRoom create(User sender, User receiver) {
        ChatRoom room = new ChatRoom();
        room.sender = sender;
        room.receiver = receiver;
        return room;
    }

    public ChatRoom(String chatRoom) {
        this.id = Long.parseLong(chatRoom);
    }
}
