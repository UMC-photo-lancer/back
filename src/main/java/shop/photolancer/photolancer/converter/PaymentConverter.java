package shop.photolancer.photolancer.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import shop.photolancer.photolancer.domain.Charge;
import shop.photolancer.photolancer.domain.Post;
import shop.photolancer.photolancer.domain.User;
import shop.photolancer.photolancer.domain.enums.ChargeStatus;
import shop.photolancer.photolancer.domain.enums.NoteType;
import shop.photolancer.photolancer.domain.enums.PaymentMethodType;
import shop.photolancer.photolancer.domain.mapping.UserPhoto;
import shop.photolancer.photolancer.repository.UserRepository;
import shop.photolancer.photolancer.web.dto.PaymentResponseDto;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class PaymentConverter {

    private UserRepository userRepository;

    public Charge toCharge(User user, Integer amount, String paymentMethod) {
        return Charge.builder()
                .user(user)
                .amount(amount)
                .paymentMethod(PaymentMethodType.KAKAO)
                .note(NoteType.CHARGE)
                .build();
    }

    public List<PaymentResponseDto.TradeLogDto> toTradeLogDtoList(List<Charge> charges){
        return charges.stream()
                .map(this::toTradeLogDto)
                .collect(Collectors.toList());
    }

    public PaymentResponseDto.TradeLogDto toTradeLogDto(Charge charge){
        return PaymentResponseDto.TradeLogDto.builder()
                .point(charge.getAmount())
                .log(charge.getNote())
                .createdAt(charge.getCreatedAt())
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
}
