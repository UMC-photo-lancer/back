package shop.photolancer.photolancer.domain;

import lombok.*;
import shop.photolancer.photolancer.domain.base.BaseEntity;
import shop.photolancer.photolancer.domain.enums.ChargeStatus;
import shop.photolancer.photolancer.domain.enums.NoteType;
import shop.photolancer.photolancer.domain.enums.PaymentMethodType;

import javax.persistence.*;

@Entity
@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Charge extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private PaymentMethodType paymentMethod;

    @Column(nullable = false)
    private Integer amount;

    @Enumerated(EnumType.STRING)
    private NoteType note;
}
