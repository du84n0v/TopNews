package top.news.exception;

public class AppBadRequestException extends RuntimeException{
    public AppBadRequestException(String message){
        super(message);
    }
}
