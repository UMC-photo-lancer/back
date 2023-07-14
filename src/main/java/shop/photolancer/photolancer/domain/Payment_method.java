package shop.photolancer.photolancer.domain;

import lombok.*;
import shop.photolancer.photolancer.domain.enums.PaymentMethodType;

import javax.persistence.*;

@Entity
@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment_method {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private PaymentMethodType paymentMethodType;

    private String bank;

    private int accountNumber;
}
