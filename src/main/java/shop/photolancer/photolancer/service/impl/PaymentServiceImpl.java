package shop.photolancer.photolancer.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.photolancer.photolancer.converter.AccountConverter;
import shop.photolancer.photolancer.converter.PaymentConverter;
import shop.photolancer.photolancer.domain.*;
import shop.photolancer.photolancer.domain.enums.NotificationType;
import shop.photolancer.photolancer.domain.mapping.UserPhoto;
import shop.photolancer.photolancer.repository.*;
import shop.photolancer.photolancer.service.PaymentService;
import shop.photolancer.photolancer.web.dto.PaymentResponseDto;
import org.springframework.data.domain.Pageable;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
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
        user.updateNotification();

        Notification chargeNotification = paymentConverter.toChargeNotification(user, amount);

        notificationRepository.save(chargeNotification);

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
        user.updateNotification();

        Notification exchangeNotification = paymentConverter.toExchangeNotification(user, point);

        chargeRepository.save(charge);
        notificationRepository.save(exchangeNotification);
    }

    @Override
    @Transactional
    public void purchase(Long postId, User user){
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NoSuchElementException("Post not found"));
        User postUser = post.getUser();
        Integer point = post.getPoint();

        Charge charge = paymentConverter.toPurchaseLog(user, -point);

        UserPhoto userPhoto = paymentConverter.toPurchase(post, user);

        user.updatePoint(-point);
        postUser.updatePoint(point);
        user.updateNotification();
        postUser.updateNotification();

        Notification purchaseNotification = paymentConverter.toPurchaseNotification(user, postUser, point);

        Notification saleNotification = paymentConverter.toSaleNotification(user, postUser, point);

        chargeRepository.save(charge);
        userPhotoRepository.save(userPhoto);
        notificationRepository.save(purchaseNotification);
        notificationRepository.save(saleNotification);

    }
}
