package shop.photolancer.photolancer.exception;

public class ResponseMessage {

    //chat
    public static final String FOLLOWING_CHATS_READ_SUCCESS = "팔로잉 채팅 목록 불러오기 성공";
    public static final String MESSAGE_READ_SUCCESS = "채팅 내용 불러오기 성공";

    //payment
    public static final String CHARGE_POINT_SUCCESS = "포인트 충전 성공";
    public static final String TRADE_LOG_READ_SUCCESS = "거래 내역 조회 성공";
    public static final String PURCHASE_READ_SUCCESS = "사진 구매 창 불러오기 성공";
    public static final String PURCHASE_SUCCESS = "사진 구매 성공";
    public static final String AMOUT_EXIT_ERROR = "충전 금액 누락 오류";
    public static final String  PAYMENTMETHOD_EXIT_ERROR = "결제 방식 누락 오류";
    public static final String  BANK_EXIT_ERROR = "은행 정보 누락 오류";
    public static final String ACCOUNT_NUMBER_EXIT_ERROR = "계좌 번호 누락 오류";

    //account
    public static final String ADD_ACCOUNT_SUCCESS = "계좌 추가 성공";

    public static final String POST_UPLOAD_SUCCESS = "포스트 업로드 성공";

}
