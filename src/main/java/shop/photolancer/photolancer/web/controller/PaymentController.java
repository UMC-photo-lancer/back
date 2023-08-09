package shop.photolancer.photolancer.web.controller;

import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.photolancer.photolancer.converter.PaymentConverter;
import shop.photolancer.photolancer.domain.Charge;
import shop.photolancer.photolancer.domain.User;
import shop.photolancer.photolancer.exception.ResponseMessage;
import shop.photolancer.photolancer.exception.StatusCode;
import shop.photolancer.photolancer.service.PaymentService;
import shop.photolancer.photolancer.service.impl.UserServiceImpl;
import shop.photolancer.photolancer.web.dto.PaymentRequestDto;
import shop.photolancer.photolancer.web.dto.PaymentResponseDto;
import shop.photolancer.photolancer.web.dto.base.DefaultRes;

import org.slf4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "결제 관련 API")
@RestController
@RequiredArgsConstructor
public class PaymentController {

    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);
    private final UserServiceImpl userService;
    private final PaymentService paymentService;
    private final PaymentConverter paymentConverter;

    @ApiOperation(value = "포인트 충전 API")
    @ApiResponse(code = 200, message = "포인트 충전 성공")
    @PostMapping("/my-profile/charge")
    public ResponseEntity charge(@RequestBody PaymentRequestDto.ChargeDto request){
        try {
            logger.info("Received request: method={}, path={}, description={}", "POST", "/my-profile/charge", "포인트 충전 API");

            User user = userService.getCurrentUser();

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
    @ApiImplicitParam(name = "page", value = "페이지 번호", dataType = "int", example = "1", paramType = "query")
    @GetMapping("/my-profile/trade-log")
    public ResponseEntity tradeLog(@RequestParam(defaultValue = "1") Integer page){
        try {
            logger.info("Received request: method={}, path={}, description={}", "GET", "/my-profile/trade-log", "거래 내역 조회 API");

            User user = userService.getCurrentUser();

            List<Charge> charges = paymentService.getAllCharges(user, page);
            List<PaymentResponseDto.TradeLogDto> response = paymentConverter.toTradeLogDtoList(charges);

            return new ResponseEntity( DefaultRes.res(StatusCode.OK, ResponseMessage.TRADE_LOG_READ_SUCCESS, response), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "환전 창 불러오기 API")
    @ApiResponse(code = 200, message = "환전 창 불러오기 성공")
    @GetMapping("/my-profile/exchange")
    public ResponseEntity exchangeWindow(){
        try {
            logger.info("Received request: method={}, path={}, description={}", "GET", "/my-profile/exchange", "환전 창 불러오기 API");

            User user = userService.getCurrentUser();

            List<PaymentResponseDto.ExchangeDto> response = paymentService.getExchange(user);

            return new ResponseEntity( DefaultRes.res(StatusCode.OK, ResponseMessage.EXCHANGE_READ_SUCCESS, response), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "포인트 환전하기 API")
    @ApiResponse(code = 200, message = "포인트 환전 성공")
    @PostMapping("/my-profile/exchange")
    public ResponseEntity exchange(@RequestBody PaymentRequestDto.ExchangeDto request){
        try {
            logger.info("Received request: method={}, path={}, description={}", "POST", "/my-profile/exchange", "포인트 환전 API");

            User user = userService.getCurrentUser();

            String bank = request.getBank();
            String accountNumber = request.getAccountNumber();
            Integer point = request.getPoint();

            if (bank == null){
                return new ResponseEntity( DefaultRes.res(StatusCode.BAD_REQUEST, ResponseMessage.BANK_EXIT_ERROR), HttpStatus.BAD_REQUEST);
            } else if (accountNumber==null) {
                return new ResponseEntity( DefaultRes.res(StatusCode.BAD_REQUEST, ResponseMessage.ACCOUNT_NUMBER_EXIT_ERROR), HttpStatus.BAD_REQUEST);
            } else if (point==null) {
                return new ResponseEntity( DefaultRes.res(StatusCode.BAD_REQUEST, ResponseMessage.POINT_EXIT_ERROR), HttpStatus.BAD_REQUEST);
            }

            paymentService.exchange(user, bank, accountNumber, point);

            return new ResponseEntity( DefaultRes.res(StatusCode.OK, ResponseMessage.EXCHANGE_POINT_SUCCESS), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "사진 구매 창 불러오기 API")
    @ApiImplicitParam(name = "post-id", value = "게시물 ID", required = true, dataType = "Long", example = "1", paramType = "path")
    @ApiResponse(code = 200, message = "사진 구매 창 불러오기 성공")
    @GetMapping("/{post-id}/purchase")
    public ResponseEntity purchaseWindow(@PathVariable("post-id") Long postId) {
        try {
            logger.info("Received request: method={}, path={}, description={}", "GET", "/{post-id}/purchase", "사진 구매 창 불러오기 API");

            User user = userService.getCurrentUser();

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
            logger.info("Received request: method={}, path={}, description={}", "POST", "/{post-id}/purchase", "사진 구매 API");

            User user = userService.getCurrentUser();

            paymentService.purchase(postId, user);

            return new ResponseEntity( DefaultRes.res(StatusCode.OK, ResponseMessage.PURCHASE_SUCCESS), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }

}
