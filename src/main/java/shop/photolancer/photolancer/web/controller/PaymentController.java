package shop.photolancer.photolancer.web.controller;

import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.photolancer.photolancer.converter.PaymentConverter;
import shop.photolancer.photolancer.domain.Charge;
import shop.photolancer.photolancer.domain.User;
import shop.photolancer.photolancer.exception.ResponseMessage;
import shop.photolancer.photolancer.exception.StatusCode;
import shop.photolancer.photolancer.repository.UserRepository;
import shop.photolancer.photolancer.service.PaymentService;
import shop.photolancer.photolancer.web.dto.PaymentRequestDto;
import shop.photolancer.photolancer.web.dto.PaymentResponseDto;
import shop.photolancer.photolancer.web.dto.base.DefaultRes;

import java.util.List;
import java.util.NoSuchElementException;

@Api(tags = "결제 관련 API")
@RestController
@Slf4j
@RequiredArgsConstructor
public class PaymentController {

    public final PaymentService paymentService;
    public final UserRepository userRepository;
    public final PaymentConverter paymentConverter;

    @ApiOperation(value = "포인트 충전 API")
    @ApiResponse(code = 200, message = "포인트 충전 성공")
    @PostMapping("/my-profile/charge")
    public ResponseEntity charge(@RequestBody PaymentRequestDto.ChargeDto request){
        try {
            //추후 유저 인증 구현
            //
            Long userId = Long.valueOf(1);

            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new NoSuchElementException("User not found"));

            //

            Integer amount = request.getAmount();
            String paymentMethod = request.getPaymentMethod();

            if (amount == null){
                return new ResponseEntity( DefaultRes.res(StatusCode.BAD_REQUEST, ResponseMessage.AMOUT_EXIT_ERROR), HttpStatus.BAD_REQUEST);
            } else if (paymentMethod==null) {
                return new ResponseEntity( DefaultRes.res(StatusCode.BAD_REQUEST, ResponseMessage.PAYMENTMETHOD_EXIT_ERROR), HttpStatus.BAD_REQUEST);
            }

            paymentService.charge(user, amount, paymentMethod);

            return new ResponseEntity( DefaultRes.res(StatusCode.OK, ResponseMessage.CHARGE_POINT_SUCCESS), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "거래 내역 조회 API")
    @ApiResponse(code = 200, message = "거래 내역 조회 성공")
    @GetMapping("/my-profile/trade-log")
    public ResponseEntity tradeLog(){
        try {
            //추후 유저 인증 구현
            //
            Long userId = Long.valueOf(1);

            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new NoSuchElementException("User not found"));
            //

            List<Charge> charges = paymentService.getAllCharges(user);
            List<PaymentResponseDto.TradeLogDto> response = paymentConverter.toTradeLogDtoList(charges);

            return new ResponseEntity( DefaultRes.res(StatusCode.OK, ResponseMessage.TRADE_LOG_READ_SUCCESS, response), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "사진 구매 창 불러오기 API")
    @ApiImplicitParam(name = "post-id", value = "게시물 ID", required = true, dataType = "Long", example = "1", paramType = "path")
    @ApiResponse(code = 200, message = "사진 구매 창 불러오기 성공")
    @GetMapping("/{post-id}/purchase")
    public ResponseEntity purchaseWindow(@PathVariable("post-id") Long postId){
        try {
            //추후 유저 인증 구현
            //
            Long userId = Long.valueOf(1);

            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new NoSuchElementException("User not found"));
            //

            PaymentResponseDto.PurchaseDto response = paymentService.getPurchase(postId, user);

            return new ResponseEntity( DefaultRes.res(StatusCode.OK, ResponseMessage.PURCHASE_READ_SUCCESS, response), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "사진 구매 API")
    @ApiImplicitParam(name = "post-id", value = "게시물 ID", required = true, dataType = "Long", example = "1", paramType = "path")
    @ApiResponse(code = 200, message = "사진 구매 성공")
    @PostMapping("/{post-id}/purchase")
    public ResponseEntity purchase(@PathVariable("post-id") Long postId){
        try {
            //추후 유저 인증 구현
            //
            Long userId = Long.valueOf(1);

            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new NoSuchElementException("User not found"));
            //

            paymentService.purchase(postId, user);

            return new ResponseEntity( DefaultRes.res(StatusCode.OK, ResponseMessage.PURCHASE_SUCCESS), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }

}
