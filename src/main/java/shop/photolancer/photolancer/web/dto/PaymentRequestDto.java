package shop.photolancer.photolancer.web.dto;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.Setter;

public class PaymentRequestDto {

    @Getter @Setter
    public static class ChargeDto{
        @ApiModelProperty(example = "10000")
        @ApiParam(name = "amount", value = "포인트 충전 금액", required = true)
        private Integer amount;

        @ApiModelProperty(example = "KAKAO")
        @ApiParam(name = "paymentMethod", value = "충전 방식", required = true)
        private String  paymentMethod;
    }

    @Getter @Setter
    public static class ExchangeDto{
        @ApiModelProperty(example = "농협")
        @ApiParam(name = "bank", value = "은행", required = true)
        private String bank;

        @ApiModelProperty(example = "100151140101")
        @ApiParam(name = "accountNumber", value = "계좌 번호", required = true)
        private String accountNumber;

        @ApiModelProperty(example = "5000")
        @ApiParam(name = "amount", value = "환전할 포인트", required = true)
        private Integer point;
    }

}
