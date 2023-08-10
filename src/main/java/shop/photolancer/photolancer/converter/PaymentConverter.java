package shop.photolancer.photolancer.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import shop.photolancer.photolancer.domain.Charge;
import shop.photolancer.photolancer.domain.Notification;
import shop.photolancer.photolancer.domain.Post;
import shop.photolancer.photolancer.domain.User;
import shop.photolancer.photolancer.domain.enums.NoteType;
import shop.photolancer.photolancer.domain.enums.NotificationType;
import shop.photolancer.photolancer.domain.enums.PaymentMethodType;
import shop.photolancer.photolancer.domain.mapping.UserPhoto;
import shop.photolancer.photolancer.repository.UserRepository;
import shop.photolancer.photolancer.web.dto.PaymentResponseDto;
import shop.photolancer.photolancer.web.dto.UserResponseDto;

import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class PaymentConverter {

    private UserRepository userRepository;

    NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.KOREA);

    public Charge toCharge(User user, Integer amount, String paymentMethod) {
        if (paymentMethod =="kakao") {
            PaymentMethodType paymentMethodType = PaymentMethodType.KAKAO;
        } else if(paymentMethod=="toss"){
            PaymentMethodType paymentMethodType = PaymentMethodType.TOSS;
        } else if(paymentMethod=="card"){
            PaymentMethodType paymentMethodType = PaymentMethodType.CARD;
        }

        return Charge.builder()
                .user(user)
                .amount(amount)
                .paymentMethod(PaymentMethodType.KAKAO)
                .note(NoteType.CHARGE)
                .build();
    }

    public Charge toExchange(User user, Integer amount){
        return Charge.builder()
                .user(user)
                .amount(amount)
                .note(NoteType.EXCHANGE)
                .build();
    }

    public Charge toPurchaseLog(User user, Integer amount){
        return Charge.builder()
                .user(user)
                .amount(amount)
                .note(NoteType.PURCHASE)
                .build();
    }

    public List<PaymentResponseDto.TradeLogDto> toTradeLogDtoList(List<Charge> charges){
        return charges.stream()
                .map(this::toTradeLogDto)
                .collect(Collectors.toList());
    }

    public PaymentResponseDto.TradeLogDto toTradeLogDto(Charge charge){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedCreatedAt = charge.getCreatedAt().format(formatter);

        return PaymentResponseDto.TradeLogDto.builder()
                .point(charge.getAmount())
                .log(charge.getNote())
                .createdAt(formattedCreatedAt)
                .build();
    }

    public PaymentResponseDto.PurchaseDto toPurchaseWindow(Integer price, Integer userPoint){
        return PaymentResponseDto.PurchaseDto.builder()
                .price(price)
                .userPoint(userPoint)
                .build();
    }

    public UserPhoto toPurchase(Post post, User user){
        return UserPhoto.builder()
                .post(post)
                .user(user)
                .build();
    }

    public PaymentResponseDto.PaymentUserDto toUserInfo(User user){
        return PaymentResponseDto.PaymentUserDto.builder()
                .userId(user.getId())
                .point(user.getPoint())
                .build();
    }

    public Notification toChargeNotification(User user, Integer amount){
        return Notification.builder()
                .message("포인트를 충전했습니다.")
                .type(NotificationType.POINT)
                .point("+"+numberFormat.format(amount)+" Point")
                .userPoint("잔여 "+numberFormat.format(user.getPoint())+" Point")
                .user(user)
                .build();
    }

    public Notification toExchangeNotification(User user, Integer point){
        return Notification.builder()
                .message("포인트를 환전했습니다.")
                .type(NotificationType.POINT)
                .point("-"+numberFormat.format(point)+" Point")
                .userPoint("잔여 "+numberFormat.format(user.getPoint())+" Point")
                .user(user)
                .build();
    }

    public Notification toPurchaseNotification(User user, User postUser, Integer point){
        return Notification.builder()
                .message("Lv. " + postUser.getLevel() + " " + postUser.getNickname() + "님의 게시글을 구매했습니다.")
                .type(NotificationType.POINT)
                .point("-"+numberFormat.format(point)+" Point")
                .userPoint("잔여 "+numberFormat.format(user.getPoint())+" Point")
                .user(user)
                .build();
    }

    public Notification toSaleNotification(User user, User postUser, Integer point){
        return Notification.builder()
                .message("Lv. " + user.getLevel() + " " + user.getNickname() + "님이 " + postUser.getNickname() + "님의 게시글을 구매했습니다.")
                .type(NotificationType.POINT)
                .point("+"+numberFormat.format(point)+" Point")
                .userPoint("잔여 "+numberFormat.format(postUser.getPoint())+" Point")
                .user(postUser)
                .build();
    }

}
