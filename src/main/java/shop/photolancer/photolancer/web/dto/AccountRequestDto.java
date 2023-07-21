package shop.photolancer.photolancer.web.dto;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.Setter;

public class AccountRequestDto {

    @Getter @Setter
    public static class AccountDto{
        @ApiModelProperty(example = "농협")
        @ApiParam(name = "bank", value = "은행", required = true)
        private String bank;

        @ApiModelProperty(example = "100151140101")
        @ApiParam(name = "accountNumber", value = "계좌 번호", required = true)
        private String accountNumber;
    }

}
