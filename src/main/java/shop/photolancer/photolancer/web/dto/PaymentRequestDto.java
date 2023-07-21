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

}
