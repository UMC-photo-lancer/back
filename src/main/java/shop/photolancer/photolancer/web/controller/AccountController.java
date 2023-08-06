package shop.photolancer.photolancer.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import shop.photolancer.photolancer.domain.User;
import shop.photolancer.photolancer.exception.ResponseMessage;
import shop.photolancer.photolancer.exception.StatusCode;
import shop.photolancer.photolancer.service.AccountService;
import shop.photolancer.photolancer.service.PaymentService;
import shop.photolancer.photolancer.service.impl.UserServiceImpl;
import shop.photolancer.photolancer.web.dto.AccountRequestDto;
import shop.photolancer.photolancer.web.dto.PaymentResponseDto;
import shop.photolancer.photolancer.web.dto.base.DefaultRes;

import java.util.List;

@Api(tags = "계좌 관련 API")
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/setting/account")
public class AccountController {

    private final AccountService accountService;
    private final UserServiceImpl userService;
    private final PaymentService paymentService;

    @ApiOperation(value = "계좌 목록 불러오기 API")
    @ApiResponse(code = 200, message = "계좌 목록 불러오기 성공")
    @GetMapping()
    public ResponseEntity exchangeWindow(){
        try {
            User user = userService.getCurrentUser();

            List<PaymentResponseDto.ExchangeDto> response = paymentService.getExchange(user);

            return new ResponseEntity( DefaultRes.res(StatusCode.OK, ResponseMessage.ACCOUNT_READ_SUCCESS, response), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "계좌 추가 API")
    @ApiResponse(code = 200, message = "계좌 추가 성공")
    @PostMapping()
    public ResponseEntity addAccount(@RequestBody AccountRequestDto.AccountDto request){
        try {
            User user = userService.getCurrentUser();

            String bank = request.getBank();
            String accountNumber = request.getAccountNumber();

            if (bank == null){
                return new ResponseEntity( DefaultRes.res(StatusCode.BAD_REQUEST, ResponseMessage.BANK_EXIT_ERROR), HttpStatus.BAD_REQUEST);
            } else if (accountNumber==null) {
                return new ResponseEntity( DefaultRes.res(StatusCode.BAD_REQUEST, ResponseMessage.ACCOUNT_NUMBER_EXIT_ERROR), HttpStatus.BAD_REQUEST);
            }

            accountService.add(user, bank, accountNumber);

            return new ResponseEntity( DefaultRes.res(StatusCode.OK, ResponseMessage.ADD_ACCOUNT_SUCCESS), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "메인 계좌 설정 API")
    @ApiImplicitParam(name = "account-id", value = "계좌 ID", required = true, dataType = "Long", example = "1", paramType = "path")
    @ApiResponse(code = 200, message = "메인 계좌 설정 성공")
    @PatchMapping("/{account-id}")
    public ResponseEntity mainAccount(@PathVariable("account-id") Long accountId){
        try {
            User user = userService.getCurrentUser();

            accountService.updateIsMain(user, accountId);

            return new ResponseEntity( DefaultRes.res(StatusCode.OK, ResponseMessage.ACCOUNT_MAIN_SUCCESS), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "계좌 수정 API")
    @ApiImplicitParam(name = "account-id", value = "계좌 ID", required = true, dataType = "Long", example = "1", paramType = "path")
    @ApiResponse(code = 200, message = "계좌 수정 성공")
    @PutMapping("/{account-id}")
    public ResponseEntity updateAccount(@PathVariable("account-id") Long accountId, @RequestBody AccountRequestDto.AccountDto request){
        try {
            User user = userService.getCurrentUser();

            String bank = request.getBank();
            String accountNumber = request.getAccountNumber();

            if (bank == null){
                return new ResponseEntity( DefaultRes.res(StatusCode.BAD_REQUEST, ResponseMessage.BANK_EXIT_ERROR), HttpStatus.BAD_REQUEST);
            } else if (accountNumber==null) {
                return new ResponseEntity( DefaultRes.res(StatusCode.BAD_REQUEST, ResponseMessage.ACCOUNT_NUMBER_EXIT_ERROR), HttpStatus.BAD_REQUEST);
            }

            accountService.updateAccount(user, accountId, bank, accountNumber);

            return new ResponseEntity( DefaultRes.res(StatusCode.OK, ResponseMessage.ACCOUNT_UPDATE_SUCCESS), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "계좌 삭제 API")
    @ApiImplicitParam(name = "account-id", value = "계좌 ID", required = true, dataType = "Long", example = "1", paramType = "path")
    @ApiResponse(code = 200, message = "계좌 삭제 성공")
    @DeleteMapping("/{account-id}")
    public ResponseEntity deleteAccount(@PathVariable("account-id") Long accountId){
        try {
            User user = userService.getCurrentUser();

            accountService.deleteAccount(user, accountId);

            return new ResponseEntity( DefaultRes.res(StatusCode.OK, ResponseMessage.ACCOUNT_DELETE_SUCCESS), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }

}
