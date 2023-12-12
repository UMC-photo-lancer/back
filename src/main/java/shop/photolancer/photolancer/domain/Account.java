package shop.photolancer.photolancer.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String bank;

    @Column(nullable = false)
    private String accountNumber;

    @Column(columnDefinition = "Boolean default False")
    private Boolean isMain;

    public void updateIsMain(Boolean isMain){
        this.isMain = isMain;
    }

    public void updateAccount(String  bank, String accountNumber){
        this.bank = bank;
        this.accountNumber = accountNumber;
    }
}
