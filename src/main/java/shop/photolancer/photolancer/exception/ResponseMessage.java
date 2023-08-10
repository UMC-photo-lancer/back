package shop.photolancer.photolancer.exception;

public class ResponseMessage {

    //chat
    public static final String CHATS_READ_SUCCESS = "채팅 목록 불러오기 성공";
    public static final String CREATE_CHAT_ROOM_SUCCESS = "채팅방 생성 성공";
    public static final String MESSAGE_READ_SUCCESS = "채팅방 불러오기 성공";
    public static final String SEARCH_CHAT_ROOM_SUCCESS = "채팅방 검색 성공";
    public static final String MESSAGE_SAVE_SUCCESS = "메시지 저장 성공";

    //payment
    public static final String CHARGE_POINT_SUCCESS = "포인트 충전 성공";
    public static final String TRADE_LOG_READ_SUCCESS = "거래 내역 조회 성공";
    public static final String EXCHANGE_READ_SUCCESS = "환전 창 불러오기 성공";
    public static final String EXCHANGE_POINT_SUCCESS = "포인트 환전 성공";
    public static final String PURCHASE_READ_SUCCESS = "사진 구매 창 불러오기 성공";
    public static final String PURCHASE_SUCCESS = "사진 구매 성공";
    public static final String AMOUT_EXIT_ERROR = "충전 금액 누락 오류";
    public static final String  PAYMENTMETHOD_EXIT_ERROR = "결제 방식 누락 오류";
    public static final String  BANK_EXIT_ERROR = "은행 정보 누락 오류";
    public static final String ACCOUNT_NUMBER_EXIT_ERROR = "계좌 번호 누락 오류";
    public static final String POINT_EXIT_ERROR = "포인트 누락 오류";

    //account
    public static final String ACCOUNT_READ_SUCCESS = "계좌 목록 불러오기 성공";
    public static final String ADD_ACCOUNT_SUCCESS = "계좌 추가 성공";
    public static final String ACCOUNT_MAIN_SUCCESS = "메인 계좌 설정 성공";
    public static final String ACCOUNT_UPDATE_SUCCESS = "계좌 수정 성공";
    public static final String ACCOUNT_DELETE_SUCCESS = "계좌 삭제 성공";

    // post
    public static final String POST_UPLOAD_SUCCESS = "포스트 업로드 성공";
    public static final String POST_LIKE_SUCCESS = "포스트 좋아요 성공";

    public static final String POST_UPDATE_SUCCESS = "포스트 수정 성공";

    public static final String POST_SHARE_SUCCESS = "포스트 공유 성공";

    public static final String POST_DELETE_SUCCESS = "포스트 삭제 성공";

    public static final String POST_SAVE_SUCCESS = "포스트 저장 성공";

    // notice
    public static final String NOTICE_UPLOAD_SUCCESS = "공지 업로드 성공";
    public static final String NOTICE_DELETE_SUCCESS = "공지 삭제 성공";

    public static final String NOTICE_UPDATE_SUCCESS = "공지 수정 성공";

    //comment
    public static final String COMMENT_UPLOAD_SUCCESS = "댓글 업로드 성공";
    public static final String RECOMMENT_UPLOAD_SUCCESS = "대댓글 업로드 성공";
    public static final String COMMENT_DELETE_SUCCESS = "댓글 삭제 성공";
    public static final String RECOMMENT_DELETE_SUCCESS = "대댓글 삭제 성공";

    //follow, following
    public static final String FOLLOW_REQUEST_SUCCESS = "팔로우 요청 성공";
    public static final String INVALID_REQUEST = "유효한 요청이 아닙니다";
    public static final String NOTICE_NOT_ADMIN = "관리자가 아닙니다";
    public static final String FOLLOWER_DELETE_SUCCESS = "팔로워 삭제 성공";



}
