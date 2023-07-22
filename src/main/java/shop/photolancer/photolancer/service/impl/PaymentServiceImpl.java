package shop.photolancer.photolancer.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.photolancer.photolancer.converter.AccountConverter;
import shop.photolancer.photolancer.converter.PaymentConverter;
import shop.photolancer.photolancer.domain.Account;
import shop.photolancer.photolancer.domain.Charge;
import shop.photolancer.photolancer.domain.Post;
import shop.photolancer.photolancer.domain.User;
import shop.photolancer.photolancer.domain.mapping.UserPhoto;
import shop.photolancer.photolancer.repository.AccountRepository;
import shop.photolancer.photolancer.repository.ChargeRepository;
import shop.photolancer.photolancer.repository.PostRepository;
import shop.photolancer.photolancer.repository.UserPhotoRepository;
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

        Integer amount = post.getPoint();

        user.updatePoint(-amount);

        UserPhoto userPhoto = paymentConverter.toPurchase(post, user);
        userPhotoRepository.save(userPhoto);

    }

}
