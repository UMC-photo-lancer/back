package shop.photolancer.photolancer.exception;

public class CustomExceptions{

    //결제 관련
    public static class ChareException extends RuntimeException{
        public ChareException(String message){
            super(message);
        }
    }
    public static class TradeLogException extends RuntimeException {
        public TradeLogException(String message) {
            super(message);
        }
    }
    public static class ExchangeWindowException extends RuntimeException {
        public ExchangeWindowException(String message) {
            super(message);
        }
    }
    public static class ExchangeException extends RuntimeException {
        public ExchangeException(String message) {
            super(message);
        }
    }
    public static class PurchaseWindowException extends RuntimeException {
        public PurchaseWindowException(String message) {
            super(message);
        }
    }
    public static class PurchaseException extends RuntimeException {
        public PurchaseException(String message) {
            super(message);
        }
    }

    //계좌 관련
    public static class GetAccountException extends RuntimeException {
        public GetAccountException(String message) {
            super(message);
        }
    }
    public static class AddAccountException extends RuntimeException {
        public AddAccountException(String message) {
            super(message);
        }
    }
    public static class MainAccountException extends RuntimeException {
        public MainAccountException(String message) {
            super(message);
        }
    }
    public static class UpdateAccountException extends RuntimeException {
        public UpdateAccountException(String message) {
            super(message);
        }
    }
    public static class DeleteAccountException extends RuntimeException {
        public DeleteAccountException(String message) {
            super(message);
        }
    }

    //채팅 관련
    public static class GetAllChatRoomsException extends RuntimeException {
        public GetAllChatRoomsException(String message) {
            super(message);
        }
    }
    public static class CreateRoomException extends RuntimeException {
        public CreateRoomException(String message) {
            super(message);
        }
    }
    public static class GetChatMessagesException extends RuntimeException {
        public GetChatMessagesException(String message) {
            super(message);
        }
    }
    public static class SearchRoomException extends RuntimeException {
        public SearchRoomException(String message) {
            super(message);
        }
    }
    public static class SaveMessageException extends RuntimeException{
        public SaveMessageException(String message){
            super(message);
        }
    }

}
