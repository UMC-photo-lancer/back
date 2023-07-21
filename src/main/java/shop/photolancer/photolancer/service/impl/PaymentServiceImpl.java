package shop.photolancer.photolancer.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.photolancer.photolancer.converter.PaymentConverter;
import shop.photolancer.photolancer.domain.Charge;
import shop.photolancer.photolancer.domain.Post;
import shop.photolancer.photolancer.domain.User;
import shop.photolancer.photolancer.domain.mapping.UserPhoto;
import shop.photolancer.photolancer.repository.ChargeRepository;
import shop.photolancer.photolancer.repository.PostRepository;
import shop.photolancer.photolancer.repository.UserPhotoRepository;
import shop.photolancer.photolancer.service.PaymentService;
import shop.photolancer.photolancer.web.dto.PaymentResponseDto;

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

    @Override
    @Transactional
    public Charge charge(User user, Integer amount, String paymentMethod){
        Charge charge = paymentConverter.toCharge(user, amount, paymentMethod);

        user.updatePoint(amount);

        return chargeRepository.save(charge);
    }

    @Override
    public List<Charge> getAllCharges(User user){
        return chargeRepository.findAllByUserOrderByCreatedAtDesc(user);
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
