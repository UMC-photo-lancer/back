package shop.photolancer.photolancer.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.photolancer.photolancer.converter.AccountConverter;
import shop.photolancer.photolancer.converter.PaymentConverter;
import shop.photolancer.photolancer.domain.*;
import shop.photolancer.photolancer.domain.enums.NoteType;
import shop.photolancer.photolancer.domain.enums.NotificationType;
import shop.photolancer.photolancer.domain.mapping.UserPhoto;
import shop.photolancer.photolancer.repository.*;
import shop.photolancer.photolancer.service.PaymentService;
import shop.photolancer.photolancer.web.dto.PaymentResponseDto;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentConverter paymentConverter;
    private final ChargeRepository chargeRepository;
    private final PostRepository postRepository;
    private final UserPhotoRepository userPhotoRepository;
    private final AccountRepository accountRepository;
    private final AccountConverter accountConverter;
    private final NotificationRepository notificationRepository;

    @Override
    @Transactional
    public Charge charge(User user, Integer amount, String paymentMethod){
        Charge charge = paymentConverter.toCharge(user, amount, paymentMethod);

        user.updatePoint(amount);

        return chargeRepository.save(charge);
    }

    @Override
    public List<Charge> getAllCharges(User user, Integer page){
        int pageSize = 10; // 페이지 당 출력할 아이템 수
        Pageable pageable = (Pageable) PageRequest.of(page-1, pageSize, Sort.by("createdAt").descending());
        return chargeRepository.findAllByUser(user, pageable).getContent();
    }

    @Override
    public PaymentResponseDto.PurchaseDto getPurchase(Long postId, User user){
        Integer userPoint = user.getPoint();

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NoSuchElementException("Post not found"));

        Integer price = post.getPoint();

        return paymentConverter.toPurchaseWindow(price, userPoint);
    }

    @Override
    public List<PaymentResponseDto.ExchangeDto> getExchange(User user){
        List<Account> accounts = accountRepository.findByUser(user);
        List<PaymentResponseDto.ExchangeDto> accountDTOs = new ArrayList<>();

        for (Account account : accounts) {
            PaymentResponseDto.ExchangeDto accountDTO = accountConverter.toExchange(account.getId(), account.getBank(),account.getAccountNumber(),account.getIsMain());
            accountDTOs.add(accountDTO);
        }

        return accountDTOs;
    }

    @Override
    @Transactional
    public void exchange(User user, String bank, String accountNumber, Integer point){
        Charge charge = paymentConverter.toExchange(user, -point);

        user.updatePoint(-point);

        chargeRepository.save(charge);
    }

    @Override
    @Transactional
    public void purchase(Long postId, User user){
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NoSuchElementException("Post not found"));

        Integer point = post.getPoint();

        Charge charge = paymentConverter.toPurchaseLog(user, -point);

        chargeRepository.save(charge);

        user.updatePoint(-point);

        UserPhoto userPhoto = paymentConverter.toPurchase(post, user);
        userPhotoRepository.save(userPhoto);

        Notification purchaseNote = Notification.builder()
                .message(post.getUser().getNickname() + "님의 게시글을 구매했습니다.")
                .type(NotificationType.POINT)
                .point("+"+point+" Point")
                .user(user)
                .build();
        notificationRepository.save(purchaseNote);

        Notification saleNote = Notification.builder()
                .message("Lv. " + user.getLevel() + " " + user.getNickname() + "님이 " + post.getUser().getNickname() + "님의 게시글을 구매했습니다.")
                .type(NotificationType.POINT)
                .point("-"+point+" Point")
                .user(post.getUser())
                .build();
        notificationRepository.save(saleNote);

    }

}
