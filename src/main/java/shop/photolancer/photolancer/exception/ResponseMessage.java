package shop.photolancer.photolancer.exception;

public class ResponseMessage {

    //test
    public static final String TEST_SUCCESS = "테스트 성공";

    //chat
    public static final String FOLLOWING_CHATS_READ_SUCCESS = "팔로잉 채팅 목록 불러오기 성공";
    public static final String MESSAGE_READ_SUCCESS = "채팅 내용 불러오기 성공";

    //payment
    public static final String CHARGE_POINT_SUCCESS = "포인트 충전 성공";
    public static final String AMOUT_EXIT_ERROR = "충전 금액 누락 오류";
    public static final String  PAYMENTMETHOD_EXIT_ERROR = "결제 방식 누락 오류";
}
